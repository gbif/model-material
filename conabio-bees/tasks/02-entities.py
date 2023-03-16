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
upstream = ["create-agents"]

# This is a placeholder, leave it as None
product = None


# %%
from hashlib import sha1
from pathlib import Path

import pandas as pd

# %%
data_folder = Path(data_folder)

# %%
ecoab_occurrences = pd.read_csv(data_folder / "ecoab-occurrences.csv")
ecoab_taxa = pd.read_csv(data_folder / "ecoab-occurrence-taxa.csv")

# %%
ecoab_occurrences.head()

# %%
ecoab_taxa.head()

# %%
ecoab_plantae = ecoab_taxa[ecoab_taxa["kingdom"] == "Plantae"].copy()

# %%
ecoab_plantae["material_entity_id"] = "entity:" + ecoab_plantae.index.astype("str")

# %%
# Create unique ids 
ecoab_occurrences["material_entity_id"] = ecoab_occurrences.apply(lambda x: sha1(x["occurrenceID"].encode("utf-8")).hexdigest(), axis=1)
ecoab_occurrences["entity_type"] = "MATERIAL_ENTITY"
ecoab_plantae["material_entity_id"] = ecoab_plantae.apply(lambda x: sha1(x["material_entity_id"].encode("utf-8")).hexdigest(), axis=1)
ecoab_plantae["entity_type"] = "MATERIAL_ENTITY"

# %%
entities = pd.DataFrame({"entity_id": ecoab_occurrences["material_entity_id"], "entity_type": ecoab_occurrences["entity_type"], "dataset_id": dataset_id, "entity_name": None, "entity_remarks": None})

# %%
entities = pd.concat([
    entities,
    (
    ecoab_plantae[["material_entity_id", "entity_type"]]
        .rename(columns={"material_entity_id": "entity_id"})
        .assign(
            dataset_id=dataset_id,
            entity_name=None,
            entity_remarks="note written on a specimen tag"
        )
    )
])

# %%
entities.to_csv(product["entity_table"], index=False)

# %%
