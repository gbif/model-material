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
upstream = ["create-entities"]

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
resource_relationship = pd.read_csv(upstream["create-entities"]["entity_relationship_table"])

# %%
ecoab_occurrences["material_entity_id"] = ecoab_occurrences.apply(lambda x: sha1(x["occurrenceID"].encode("utf-8")).hexdigest(), axis=1)
ecoab_occurrences["location_id"] = ecoab_occurrences.apply(lambda x: sha1(x["locationID"].encode("utf-8")).hexdigest(), axis=1)

# %%
ecoab_occurrences["event_id"] = ecoab_occurrences["locationID"] + '|' + ecoab_occurrences["eventDate"] + "|" + ecoab_occurrences["occurrenceID"]
ecoab_occurrences["event_id"] = ecoab_occurrences.apply(lambda x: sha1(x["event_id"].encode("utf-8")).hexdigest(), axis=1)

# %%
events = pd.DataFrame({
    "event_id": ecoab_occurrences["event_id"],
    "parent_event_id": None,
    "dataset_id": dataset_id,
    "location_id": ecoab_occurrences["location_id"],
    "protocol_id": None,
    "event_type": "OCCURRENCE",
    "event_name": None,
    "field_number": None,
    "event_date": ecoab_occurrences["eventDate"],
    "year": ecoab_occurrences["year"],
    "month": ecoab_occurrences["month"],
    "day": ecoab_occurrences["day"],
    "verbatim_event_date": None,
    "verbatim_locality": None,
    "verbatim_elevation": None,
    "verbatim_depth": None,
    "verbatim_coordinates": None,
    "verbatim_latitude": None,
    "verbatim_longitude": None,
    "verbatim_coordinate_system": None,
    "verbatim_srs": None,
    "habitat": None,
    "protocol_description": None,
    "sample_size_value": None,
    "sample_size_unit": None,
    "event_effort": None,
    "field_notes": None,
    "event_remarks": None
})

# %%
events.head()

# %%
events.to_csv(product["events_table"], index=False)

# %%
occurrence = pd.DataFrame({
    "occurrence_id": ecoab_occurrences["event_id"],
    "organism_id": ecoab_occurrences["material_entity_id"],
    "organism_quantity": 1,
    "organism_quantity_type": "individuals",
    "sex": ecoab_occurrences["sex"],
    "life_stage": None,
    "reproductive_condition": None,
    "behavior": None,
    "establishment_means": None,
    "occurrence_status": "PRESENT",
    "pathway": None,
    "degree_of_establishment": None,
    "georeference_verification_status": None,
    "occurrence_remarks": None,
    "information_withheld": None,
    "data_generalizations": None,
    "recorded_by": ecoab_occurrences["recordedBy"],            
    "recorded_by_id": ecoab_occurrences["recordedByID"], 
    "associated_media": None,
    "associated_occurrences": None,
    "associated_taxa": None
})

# %%
occurrence.head()

# %%
occurrence.to_csv(product["occurrence_table"], index=False)

# %%
occurrence_evidence = pd.DataFrame({
    "occurrence_id": ecoab_occurrences["event_id"],
    "entity_id": ecoab_occurrences["material_entity_id"]
})

# %%
occurrence_evidence.head()

# %%
occurrence_evidence["entity_id"].is_unique

# %%
occurrence_evidence.to_csv(product["occurrence_evidence"], index=False)

# %%
