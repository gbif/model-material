# ---
# jupyter:
#   jupytext:
#     text_representation:
#       extension: .py
#       format_name: percent
#       format_version: '1.3'
#       jupytext_version: 1.14.4
#   kernelspec:
#     display_name: Python 3 (ipykernel)
#     language: python
#     name: python3
# ---

# %%
# Add description here
#
# *Note:* You can open this file as a notebook (JupyterLab: right-click on it in the side bar -> Open With -> Notebook)


# %%
# Uncomment the next two lines to enable auto reloading for imported modules
# # %load_ext autoreload
# # %autoreload 2
# For more info, see:
# https://docs.ploomber.io/en/latest/user-guide/faq_index.html#auto-reloading-code-in-jupyter

# %% tags=["parameters"]
# If this task has dependencies, list them them here
# (e.g. upstream = ['some_task']), otherwise leave as None.
upstream = None

# This is a placeholder, leave it as None
product = None


# %%
import pandas as pd
import json
import requests
import hashlib
from string import Template
from pathlib import Path

# %%
import urllib3
urllib3.disable_warnings(urllib3.exceptions.InsecureRequestWarning)

# %%
SIAGRO_URL_ENDPOINT = "https://maices-siagro.conabio.gob.mx/graphql/"
PAGE_SIZE = 5000

# %%
output_data_path = Path(product["data"])
output_data_path.unlink(missing_ok=True)

# %%
total_records_query = """
{
  countCaracteristica_cuantitativas
}
"""

# %%
assertions_record_page_query = Template("""
{
  caracteristica_cuantitativas(
    pagination: {
      limit: $page_size
      offset: $offset
    }
    search: {
        field:registro_id value:null operator:ne
    })
  {
    assertion_target_id: registro_id
    id
    assertion_type: nombre_corto
    assertion_value_numeric: valor
    assertion_unit: unidad
    assertion_remarks: comentarios
    metodo_id
  }
}
""")

# %%
r = requests.post(SIAGRO_URL_ENDPOINT, json={"query": total_records_query}, verify=False)

# %%
cnt_records = r.json()["data"]["countCaracteristica_cuantitativas"]
print(f"There are {cnt_records} records")

# %%
record_pages = []

# %%
for i in range(0, cnt_records, PAGE_SIZE):
    payload = {
        "query": assertions_record_page_query.substitute(offset=i, page_size=PAGE_SIZE)
    }
    r = requests.post(SIAGRO_URL_ENDPOINT, 
                     json=payload,
                     verify=False)
    records = r.json()["data"]["caracteristica_cuantitativas"]
    records_df = pd.DataFrame(records)
    records_df["assertion_target_type"] = "MATERIAL_ENTITY"
    records_df["assertion_id"] = (('quantitative_id_' + records_df['id'])
                               .str
                               .encode('utf-8')
                               .apply(lambda x: hashlib.sha1(x).hexdigest()))
    records_df.loc[pd.notna(records_df["metodo_id"]), "protocol_id"] = (
        records_df.loc[pd.notna(records_df["metodo_id"]), "metodo_id"]
        .str
        .encode("utf-8")
        .apply(lambda x: hashlib.sha1(x).hexdigest())
    )
    try:
        records_df[['id_x', 'proyecto']] = (records_df['metodo_id']
                                            .str
                                            .split('_', n=1, expand=True))
    except ValueError:
        print('Hubo un error en el split')
        pass
    record_pages.append(records_df)
    # records_df = records_df.drop(columns=["id"])
    # records_df.to_csv(
    #     output_data_path,
    #     mode='a',
    #     sep='\t',
    #     header=not output_data_path.exists(),
    #     index=False
    # )

# %%
records_df = pd.concat(record_pages, ignore_index=True)

# %%
records_selected = records_df.loc[(records_df['proyecto'] == 'FZ001') |  records_df['proyecto'].str.contains('FZ016'), ]

# %%
records_selected.to_csv(product['data'], index=False, sep='\t')

# %%
