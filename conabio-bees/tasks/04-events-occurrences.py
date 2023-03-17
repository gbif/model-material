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
upstream = ["create-entitties"]

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
resource_relationship = pd.read_csv(upstream["create-entitties"]["entity_relationship_table"])

# %%
ecoab_occurrences["material_entity_id"] = ecoab_occurrences.apply(lambda x: sha1(x["occurrenceID"].encode("utf-8")).hexdigest(), axis=1)
ecoab_occurrences["location_id"] = ecoab_occurrences.apply(lambda x: sha1(x["locationID"].encode("utf-8")).hexdigest(), axis=1)

# %%
ecoab_occurrences.columns

# %%
ecoab_occurrences["event_id"] = ecoab_occurrences["locationID"] + '|' + ecoab_occurrences["eventDate"]
ecoab_occurrences["event_id"] = ecoab_occurrences.apply(lambda x: sha1(x["event_id"].encode("utf-8")).hexdigest(), axis=1)

# %%
events = pd.DataFrame({
    "event_id": ecoab_occurrences["event_id"],
    "parent_event_id": None,
    "dataset_id": None,
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
events = events.drop_duplicates()

# %%
events.to_csv(product["events_table"], index=False)

# %%
occurrence_evidence = (
    pd.merge(resource_relationship, ecoab_occurrences, left_on="subject_entity_id", right_on="material_entity_id", how="left")[["object_entity_id", "event_id"]]
)
occurrence_evidence = occurrence_evidence.rename(columns={"object_entity_id": "material_entity_id"})

# %%
occurrence_evidence = pd.concat([occurrence_evidence, ecoab_occurrences[["material_entity_id", "event_id"]]])

# %%
occurrence = occurrence_evidence.groupby("event_id")["material_entity_id"].count().reset_index().rename(columns={"event_id":"occurrence_id", "material_entity_id": "organism_quantity"})

# %%
occurrence = pd.DataFrame({
    "occurrence_id": occurrence["occurrence_id"],
    "organism_id": None,
    "organism_quantity": occurrence["organism_quantity"],
    "organism_quantity_type": None,
    "sex": None,
    "life_stage": None,
    "reproductive_condition": None,
    "behavior": None,
    "establishment_means": None,
    "occurrence_status": None,
    "pathway": None,
    "degree_of_establishment": None,
    "georeference_verification_status": None,
    "occurrence_remarks": None,
    "information_withheld": None,
    "data_generalizations": None,
    "recorded_by": None,
    "recorded_by_id": None,
    "associated_media": None,
    "associated_occurrences": None,
    "associated_taxa": None
})

# %%
occurrence.to_csv(product["occurrence_table"], index=False)

# %%
occurrence_evidence.rename(columns={"material_entity_id": "entity_id", "event_id": "occurrence_id"})[["occurrence_id", "entity_id"]].to_csv(product["occurrence_evidence"], index=False)

# %%
