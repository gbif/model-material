import pandas as pd
import json
import requests
import os
import warnings
warnings.filterwarnings('ignore')

query="""{
            countCaracteristica_cualitativas
        }
        """

try:
    r = requests.post("https://maices-siagro.conabio.gob.mx/api/graphql/", json={'query': query}, verify=False)
    count_qualitative = json.loads(r.text)
    count_qualitative = count_qualitative['data']['countCaracteristica_cualitativas']

    for i in range(0,count_qualitative,5000):
        query="""{
                    caracteristica_cualitativas(pagination:{limit:5000 offset:"""+str(i)+"""}){
                        id
                        nombre
                        valor
                        nombre_corto
                        comentarios
                        metodo_id
                        registro_id
                        
                    }
                    }
        """
        try:
            r = requests.post("https://maices-siagro.conabio.gob.mx/api/graphql/", json={'query': query}, verify=False)
            json_data = json.loads(r.text)
            response_json=json_data['data']['caracteristica_cualitativas']
            pdData = pd.DataFrame(response_json)
            output_path='caracteristica_cualitativas.csv'
            pdData.to_csv(output_path, mode='a', header=not os.path.exists(output_path),index=False)
            

        except:
            print("There was an error when trying to get caracteristica_cualitativas")

except:
    print("There was an error")
