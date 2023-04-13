# ---
# jupyter:
#   jupytext:
#     text_representation:
#       extension: .py
#       format_name: percent
#       format_version: '1.3'
#       jupytext_version: 1.14.5
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
from hashlib import sha1
from pathlib import Path

import pandas as pd

# %%
data_folder = Path(data_folder)

# %%
ecoab_taxa = pd.read_csv(data_folder / "ecoab-occurrence-taxa.csv")

# %%
ecoab_taxa.head()

# %%
ecoab_taxa["taxon_id"] = ecoab_taxa.apply(lambda x: sha1(x["taxonID"].encode("utf-8")).hexdigest(), axis=1)

# %%
ecoab_taxa.loc[pd.notna(ecoab_taxa["verbatimScientificName"]), "taxon_remarks"] = '{"verbatimScientificName": "'+ ecoab_taxa["verbatimScientificName"] + '"}'

# %%
ecoab_taxa.head()

# %%
taxon = pd.DataFrame({
    "taxon_id": ecoab_taxa["taxon_id"],
    "scientific_name": ecoab_taxa["scientificName"], 
    "scientific_name_authorship": None, 
    "name_according_to": None, 
    "name_according_to_id": None, 
    "taxon_rank": ecoab_taxa["taxonRank"], 
    "taxon_source": None, 
    "scientific_name_id": None, 
    "taxon_remarks": ecoab_taxa["taxon_remarks"], 
    "parent_taxon_id": None, 
    "taxonomic_status": None, 
    "kingdom": ecoab_taxa["kingdom"], 
    "phylum": ecoab_taxa["phylum"], 
    "class": ecoab_taxa["class"], 
    "order": ecoab_taxa["order"], 
    "family": ecoab_taxa["family"], 
    "subfamily": None, 
    "genus": ecoab_taxa["genus"], 
    "subgenus": None, 
    "accepted_scientific_name": None
})

# %%
taxon.head()

# %%
taxon.to_csv(product["taxon_table"], index=False)
