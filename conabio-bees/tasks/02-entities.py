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
ecoab_occurrences = ecoab_occurrences.rename(columns={"collectionCode": "collection_code"})

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
collection_info = pd.read_csv(upstream["create-agents"]["collection_table"])

# %%
ecoab_occurrences = pd.merge(ecoab_occurrences, collection_info, on="collection_code", how="left")

# %%
ecoab_plantae.columns

# %%
material_entity = pd.DataFrame({
    "material_entity_id": ecoab_occurrences["material_entity_id"],
    "material_entity_type": 'ORGANISM',   
    "preparations": None,           
    "disposition": None,            
    "institution_code": ecoab_occurrences["institution_code"],       
    "institution_id": None,         
    "collection_code": ecoab_occurrences["collection_code"],        
    "collection_id": ecoab_occurrences["collection_id"],          
    "owner_institution_code": None, 
    "catalog_number": ecoab_occurrences["catalogNumber"],         
    "record_number": None,          
    "recorded_by": ecoab_occurrences["recordedBy"],            
    "recorded_by_id": ecoab_occurrences["recordedByID"],         
    "associated_references": None,  
    "associated_sequences": None,   
    "other_catalog_numbers": ecoab_occurrences["occurrenceID"]
})

# %%
material_entity.head()

# %%
material_entity = pd.concat([material_entity, ecoab_plantae["material_entity_id"].to_frame()])

# %%
material_entity.to_csv(product["material_entity_table"], index=False)

# %%
interactions_data = pd.read_csv(data_folder / "ecoab-interaction-data.csv")

# %%
interactions_data = pd.merge(interactions_data, ecoab_occurrences[["material_entity_id", "occurrenceID"]], on="occurrenceID").rename(columns={"material_entity_id": "subject_entity_id"})

# %%
interactions_data = pd.merge(interactions_data, ecoab_plantae[["material_entity_id", "taxonID"]], left_on="relatedResourceID", right_on="taxonID").rename(columns={"material_entity_id": "object_entity_id"})

# %%
interactions_data.head(10)

# %%
entity_relationship = pd.DataFrame({
    "entity_relationship_id": interactions_data["relationshipOfResourceID"],
    "depends_on_entity_relationship_id": None,
    "subject_entity_id": interactions_data["subject_entity_id"],
    "entity_relationship_type": interactions_data["relationshipOfResource"],
    "object_entity_id": interactions_data["object_entity_id"],
    "object_entity_iri": None,
    "entity_relationship_date": None,
    "entity_relationship_order": None
})

# %%
entity_relationship.head(10)

# %%
entity_relationship.to_csv(product["entity_relationship_table"], index=False)

# %%
