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
upstream = ["get-siagro-qualitative", "get-siagro-quantitative"]

# This is a placeholder, leave it as None
product = None


# %%
import pandas as pd

# %%
qualitative_assertions = pd.read_csv(upstream["get-siagro-qualitative"]["data"])
quantitative_assertions = pd.read_csv(upstream["get-siagro-quantitative"]["data"], low_memory=False)

# %%
qualitative_assertions.head()

# %%
quantitative_assertions.head()

# %%
pd.concat([qualitative_assertions, quantitative_assertions]).to_csv(product["data"], index=False)

# %%
