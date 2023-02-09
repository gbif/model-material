import pandas as pd
import json
import requests
import os
import warnings
warnings.filterwarnings('ignore')

query="""{
            countCaracteristica_cuantitativas
        }
        """

try:
    r = requests.post("https://maices-siagro.conabio.gob.mx/api/graphql/", json={'query': query}, verify=False)
    count_quantitative = json.loads(r.text)
    count_quantitative = count_quantitative['data']['countCaracteristica_cuantitativas']

    for i in range(0,count_quantitative,5000):
        query="""{
                    caracteristica_cuantitativas(pagination:{limit:5000 offset:"""+str(i)+"""}){
                        id
                        nombre
                        valor
                        unidad
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
            response_json=json_data['data']['caracteristica_cuantitativas']
            pdData = pd.DataFrame(response_json)
            output_path='caracteristica_cuantitativas.csv'
            pdData.to_csv(output_path, mode='a', header=not os.path.exists(output_path),index=False)
            

        except:
            print("There was an error when trying to get caracteristica_cuantitativas")

except:
    print("There was an error")