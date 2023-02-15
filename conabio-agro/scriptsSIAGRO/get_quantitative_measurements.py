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
from string import Template
from pathlib import Path

# %%
SIAGRO_URL_ENDPOINT = "https://maices-siagro.conabio.gob.mx/api/graphql/"
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
    })
  {
    llaveejemplar: registro_id
    assertion_id: id
    assertion_type: nombre_corto
    assertion_value_numeric: valor
    assertion_unit: unidad
    assertion_remarks: comentarios
  }
}
""")

# %%
r = requests.post(SIAGRO_URL_ENDPOINT, json={"query": total_records_query}, verify=False)

# %%
cnt_records = r.json()["data"]["countCaracteristica_cuantitativas"]
print(f"There are {cnt_records} records")

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
    records_df.to_csv(
        output_data_path,
        mode='a',
        header=not output_data_path.exists(),
        index=False
    )

# %%
