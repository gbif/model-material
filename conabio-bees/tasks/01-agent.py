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
import pandas as pd
from pathlib import Path
from hashlib import sha1

# %%
data_folder = Path(data_folder)

# %%
ecoab_data = pd.read_csv(data_folder / "ecoab-occurrences.csv")

# %%
ecoab_data = ecoab_data[["occurrenceID", "collectionCode"]].rename(columns={"collectionCode": "collection_code"})

# %%
ecoab_data.head()

# %%
ecoab_data["institution_code"] = ecoab_data["occurrenceID"].str.split(":").str[0]
ecoab_data["collection_type"] = "MUSEUM"
ecoab_data["grscicoll_id"] = None
ecoab_data["namespace"] = ecoab_data["institution_code"].str.cat(ecoab_data["collection_code"], sep=":")

# %%
ecoab_data

# %%
ecoab_data = (
    ecoab_data
        .drop(columns=["occurrenceID"])
        .drop_duplicates()
)

# %%
ecoab_data['collection_id'] = ecoab_data.apply(lambda x: sha1(x["namespace"].encode("utf-8")).hexdigest(), axis=1)

# %%
ecoab_data = ecoab_data[["collection_id", "collection_type", "collection_code", "institution_code", "grscicoll_id"]]

# %%
ecoab_data.to_csv(product["collection_table"], index=False)

# %%
agents = pd.DataFrame({"agent_id": ecoab_data["collection_id"], "agent_type": "COLLECTION", "preferred_agent_name": None})

# %%
agents.to_csv(product["agent_table"], index=False)
