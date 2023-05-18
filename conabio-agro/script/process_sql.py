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
upstream = ["create-entity", 
            'create-material-entity', 
            'create-location', 
            'create-georeference', 
            'create-event',
            'create-identification',
            'create-organism',
            'create-occurrence',
            'create-occurrence-evidence',
            'create-taxon',
            'create-taxon-identification'
           ]

# This is a placeholder, leave it as None
product = None


# %%
from pathlib import Path
import pandas as pd


# %%
def convert_csv_to_tsv(csv_file_path, output_path):
    df = pd.read_csv(csv_file_path)
    fn = Path(csv_file_path).with_suffix('.tsv').name
    path = Path(output_path)
    df.to_csv(path / fn, sep='\t', index=False)
    return path / fn


# %%
with open(product['log'], 'w') as f:
    for step, outfile in upstream.items():
        outfile = convert_csv_to_tsv(outfile, data_folder)
        if step == 'create-event':
            data = pd.read_csv(outfile, sep='\t')
            data = data.astype({
                'year': 'Int64',
                'month': 'Int64',
                'day': 'Int64'
            })
            data.to_csv(outfile, sep='\t', index=False)
        f.write(f'{step}\n')

# %%
