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
qualitative_assertions = pd.read_csv(upstream["get-siagro-qualitative"]["data"], sep='\t')
quantitative_assertions = pd.read_csv(upstream["get-siagro-quantitative"]["data"], low_memory=False, sep='\t')

# %%
qualitative_assertions.head()

# %%
qualitative_assertions[pd.isna(qualitative_assertions["assertion_target_id"])].head()

# %%
quantitative_assertions.head()

# %%
quantitative_assertions[pd.isna(quantitative_assertions["assertion_target_id"])].head()

# %%
# pd.concat([qualitative_assertions, quantitative_assertions]).to_csv(product["data"], index=False)

# %%
siagro = pd.concat([qualitative_assertions, quantitative_assertions])

# %%
siagro[pd.isna(siagro["assertion_target_id"])].head()

# %%
data = pd.DataFrame({
    "assertion_id": siagro["assertion_id"],                                                                                                                                                                                                   
    "assertion_target_id": siagro["assertion_target_id"],                                                                                                                                                                                            
    "assertion_target_type": siagro["assertion_target_type"],                                                                                                                                                                                          
    "assertion_parent_assertion_id": None,                                                                                                                                                                                  
    "assertion_type": siagro["assertion_type"],                                                                                                                                                                                                 
    "assertion_made_date": None,                                                                                                                                                                                            
    "assertion_effective_date": None,                                                                                                                                                                                       
    "assertion_value": siagro["assertion_value"],                                                                                                                                                                                                
    "assertion_value_numeric": siagro["assertion_value_numeric"],                                                                                                                                                                                        
    "assertion_unit": siagro["assertion_unit"],                                                                                                                                                                                                 
    "assertion_by_agent_name": None,                                                                                                                                                                                        
    "assertion_by_agent_id": None,                                                                                                                                                                                          
    "assertion_protocol": None,                                                                                                                                                                                             
    "assertion_protocol_id": siagro['protocol_id'],                                                                                                                                                                                          
    "assertion_remarks": None
})

# %%
data.head()

# %%
data[pd.isna(data["assertion_target_id"])]

# %%
data.to_csv(product["data"], index=False, sep='\t')

# %%
