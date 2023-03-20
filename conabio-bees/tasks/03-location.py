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
import pycountry

# %%
data_folder = Path(data_folder)

# %%
ecoab_occurrences = pd.read_csv(data_folder / "ecoab-occurrences.csv")
# Create unique ids 
ecoab_occurrences["material_entity_id"] = ecoab_occurrences.apply(lambda x: sha1(x["occurrenceID"].encode("utf-8")).hexdigest(), axis=1)

# %%
ecoab_occurrences.columns

# %%
ecoab_occurrences["location_id"] = ecoab_occurrences.apply(lambda x: sha1(x["locationID"].encode("utf-8")).hexdigest(), axis=1)
ecoab_occurrences["georeference"] = "locID"+ ecoab_occurrences["locationID"] +"|lat:" + ecoab_occurrences["decimalLatitude"].astype(str) + "|lon:" + ecoab_occurrences["decimalLongitude"].astype(str)
ecoab_occurrences["georeference_id"] =  ecoab_occurrences.apply(lambda x: sha1(x["georeference"].encode("utf-8")).hexdigest(), axis=1)

# %%
ecoab_occurrences = pd.merge(
    ecoab_occurrences,
    (ecoab_occurrences["country"]
        .drop_duplicates()
        .to_frame()
        .assign(country_code=lambda x: x["country"].apply(lambda y: pycountry.countries.search_fuzzy(y)[0].alpha_2))
    ),
    on="country",
    how="left"
)

# %%
location = pd.DataFrame({
    "location_id": ecoab_occurrences["location_id"],
    "parent_location_id": None,
    "higher_geography_id": None,
    "higher_geography": None,
    "continent": None,
    "water_body": None,
    "island_group": None,
    "island": None,
    "country": ecoab_occurrences["country"],
    "country_code": ecoab_occurrences["country_code"],
    "state_province": ecoab_occurrences["stateProvince"],
    "county": None,
    "municipality": ecoab_occurrences["municipality"],
    "locality": ecoab_occurrences["locality"],
    "minimum_elevation_in_meters": ecoab_occurrences["minimumElevationInMeters"],
    "maximum_elevation_in_meters": None,
    "minimum_distance_above_surface_in_meters": None,
    "maximum_distance_above_surface_in_meters": None,
    "minimum_depth_in_meters": None,
    "maximum_depth_in_meters": None,
    "vertical_datum": None,
    "location_according_to": None,
    "location_remarks": None,
    "accepted_georeference_id": None,
    "accepted_geological_context_id": None
})

# %%
location = location.drop_duplicates()

# %%
location.to_csv(product["location_table"], index=False)

# %%
georeference = pd.DataFrame({
    "georeference_id": ecoab_occurrences["georeference_id"],
    "location_id": ecoab_occurrences["location_id"],
    "decimal_latitude": ecoab_occurrences["decimalLatitude"],
    "decimal_longitude": ecoab_occurrences["decimalLongitude"],
    "geodetic_datum": None,
    "coordinate_uncertainty_in_meters": None,
    "coordinate_precision": None,
    "point_radius_spatial_fit": None,
    "footprint_wkt": None,
    "footprint_srs": None,
    "footprint_spatial_fit": None,
    "georeferenced_by": None,
    "georeferenced_date": None,
    "georeference_protocol": None,
    "georeference_sources": None,
    "georeference_remarks": None,
    "preferred_spatial_representation": None
})

# %%
georeference = georeference.drop_duplicates()

# %%
georeference.to_csv(product["georeference_table"], index=False)
