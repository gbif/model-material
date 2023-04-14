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
upstream = ["create-entities", "create-taxa"]

# This is a placeholder, leave it as None
product = None


# %%
from pathlib import Path
from hashlib import sha1

import pandas as pd

# %%
data_folder = Path(data_folder)

# %%
material_entity = pd.read_csv(upstream["create-entities"]["material_entity_table"])

# %%
material_entity.head()

# %%
organism = pd.DataFrame({
    "organism_id": material_entity["material_entity_id"],
    "organism_scope": None
})

# %%
organism.head()

# %%
ecoab_occurrences = pd.read_csv(data_folder / "ecoab-occurrences.csv")
ecoab_taxa = pd.read_csv(data_folder / "ecoab-occurrence-taxa.csv")

# %%
flat_data = pd.merge(ecoab_occurrences, material_entity, left_on="occurrenceID", right_on="other_catalog_numbers", how="left")

# %%
flat_data.columns

# %%
flat_data["taxon_id"] = flat_data.apply(lambda x: sha1(x["taxonID"].encode("utf-8")).hexdigest(), axis=1)

# %%
flat_data.head()

# %%
ecoab_plantae = ecoab_taxa[ecoab_taxa["kingdom"] == "Plantae"].copy()
ecoab_plantae["material_entity_id"] = "entity:" + ecoab_plantae.index.astype("str")
ecoab_plantae["material_entity_id"] = ecoab_plantae.apply(lambda x: sha1(x["material_entity_id"].encode("utf-8")).hexdigest(), axis=1)
ecoab_plantae["taxon_id"] = ecoab_plantae.apply(lambda x: sha1(x["taxonID"].encode("utf-8")).hexdigest(), axis=1)

# %%
ecoab_plantae.head()

# %%
identification = pd.DataFrame({
    "organism_id": flat_data["material_entity_id"], 
    "identification_type": "features", 
    "taxon_formula": "A", 
    "verbatim_identification": None, 
    "type_status": None, 
    "identified_by": flat_data["identifiedBy"], 
    "identified_by_id": flat_data["identifiedByID"], 
    "date_identified": None, 
    "identification_references": None, 
    "identification_verification_status": None, 
    "identification_remarks": None, 
    "type_designation_type": None, 
    "type_designated_by": None 
})

# %%
identification.head()

# %%
identification = pd.concat([
    identification, 
    pd.DataFrame({
        "organism_id": ecoab_plantae["material_entity_id"], 
        "identification_type": "revised taxonomy", 
        "taxon_formula": "A", 
        "verbatim_identification": None, 
        "type_status": None, 
        "identified_by": None, 
        "identified_by_id": None, 
        "date_identified": None, 
        "identification_references": None, 
        "identification_verification_status": None, 
        "identification_remarks": "note written on a specimen tag", 
        "type_designation_type": None, 
        "type_designated_by": None 
    })
])

# %%
identification.reset_index(inplace=True)

# %%
identification.insert(0, "identification_id", "identification:" + identification.index.astype("str"))
identification["identification_id"] = identification.apply(lambda x: sha1(x["identification_id"].encode("utf-8")).hexdigest(), axis=1)

# %%
identification.head()

# %%
identification.to_csv(product["identification_table"], index=False)

# %%
organism = pd.merge(organism, identification[["identification_id", "organism_id"]], how="left").rename(columns={"identification_id": "accepted_identification_id"})

# %%
organism.head()

# %%
organism.to_csv(product["organism_table"], index=False)

# %%
join_id_taxon_data = pd.merge(
    identification, 
    pd.concat([flat_data[["material_entity_id", "taxon_id"]], ecoab_plantae[["material_entity_id", "taxon_id"]]]), 
    how="left", 
    left_on="organism_id", 
    right_on="material_entity_id")

# %%
taxon_identification = pd.DataFrame({
    "taxon_id": join_id_taxon_data["taxon_id"],
    "identification_id": join_id_taxon_data["identification_id"],
    "taxon_order": 0,
    "taxon_authority": None
})

# %%
taxon_identification.head()

# %%
taxon_identification.to_csv(product["taxon_identification_table"], index=False)
