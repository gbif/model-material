'''
This script generates protocol, references (for protocols) and citations (for protocols)
dataframes in the new GBIF model structure. It first queries SIAgroBD's maize data,
then separates and reformats the "metodos" table into three separate tables
as needed for the GBIF model.
'''

import pandas as pd
import json
import requests
import os
import warnings
warnings.filterwarnings('ignore')

def getMetodos():
    '''
    Esta función ejecuta la query para obtener los métodos que existen
    en la instancia de maíces. 
    Como salida se tiene un dataframe con la información de los métodos:
    id,descripcion,referencias,link_referencias
    '''

    query="""{
                metodos(pagination:{limit:1000}){
                    id
                    descripcion
                    referencias
                    link_referencias
                }
            }
    """

    r = requests.post("https://maices-siagro.conabio.gob.mx/graphql/", json={'query': query}, verify=False)
    json_data = json.loads(r.text)
    response_json=json_data['data']['metodos']
    pdData = pd.DataFrame(response_json)
    #output_path='metodos.csv'
    #pdData.to_csv(output_path, mode='a', header=not os.path.exists(output_path),index=False)
    return pdData


def split_tuples(row):
    '''
    Separa los pares de referencias y links para tener cada par
    de (referencia, link) en una columna aparte. 
    Asume que cada protocolo tiene máximo 2 referencias.
    Para otros casos, regresa None.
    '''
    pairs = row['zip_refs']
    if pairs is None:
        res = [None, None]
    elif len(pairs) == 1:
        res = [pairs[0], None]
    elif len(pairs) == 2:
        res = [pairs[0], pairs[1]]
    else:
        res = [None, None]
    return res


def extract_year(reference):
    '''
    Extrae el año de la referencia.
    Asume que el año (2008-2011) aparece entre los primeros 50 caracteres de la referencia.
    Para otros casos, regresa NA.
    '''
    for year in range(2008, 2012):
        if reference[:50].find(str(year)) > -1:
            return year
    return pd.NA


def gbif_references_for_methods(metodos):
    '''
    Recibe un dataframe `metodos` con la tabla de métodos que está
    en zendro (versión 2023-04-12).
    
    Regresa tres dataframes `protocol_gbif`, `reference_gbif` y `citations_gbif`
    con la estructura del nuevo modelo para los protocolos,
    las referencias y citas de los protocolos.
    '''
    metodos['zip_refs'] = None
    all_pairs = []

    for i, row in metodos.iterrows():
        ref = row['referencias']
        link = row['link_referencias']

        # pair references and links as tuples in a new column
        # only proceed when number of references matches number of links
        if len(ref) == len(link): 
            pair = list(zip(ref, link))
            row['zip_refs'] = pair
            all_pairs = all_pairs + pair # create a list of all pairs that we'll use to extract unique pairs

    unique_refs = set(all_pairs) # the set of unique pairs of (reference, link)
      
    # we'll use this dict to generate an id for each reference
    # and later on, to map references to ids in the citations table
    Refs_dict = {pair:i for i, pair in enumerate(unique_refs)}

    # gbif structure for references
    reference_gbif = pd.DataFrame(Refs_dict.keys(), index=Refs_dict.values(), columns=['bibliographic_citation', 'reference_iri'])
    reference_gbif.loc[:, 'reference_year'] = reference_gbif['bibliographic_citation'].apply(extract_year)
    reference_gbif['reference_type'] = 'OTHER'
    reference_gbif['is_peer_reviewed'] = False
    reference_gbif['reference_id'] = reference_gbif.index
    # reorder
    reference_gbif = reference_gbif[[
                    'reference_id',
                    'reference_type',
                    'bibliographic_citation',
                    'reference_year',
                    'reference_iri',
                    'is_peer_reviewed']]
    
    # we want each reference in its own column
    metodos.loc[:, ['ref1', 'ref2']] = metodos.apply(split_tuples, result_type='expand', axis=1).values
    metodos.loc[:, 'ref1'] = metodos['ref1'].apply(lambda x: Refs_dict[x] if x is not None else '')
    metodos.loc[:, 'ref2'] = metodos['ref2'].apply(lambda x: Refs_dict[x] if x is not None else '')

    # gbif structure for citations
    citations_gbif = pd.melt(metodos[['id', 'ref1', 'ref2']], id_vars=['id'], value_vars=['ref1', 'ref2'])
    citations_gbif = (citations_gbif.sort_values(by='id')
                                    .rename(columns={'id': 'citation_target_id', 'value':'citation_reference_id'}))
    citations_gbif = citations_gbif.loc[citations_gbif['citation_reference_id'] != '', :]

    # additional cols
    citations_gbif['citation_target_type'] = 'PROTOCOL'
    citations_gbif['citation_type'] = 'OTHER'
    citations_gbif['citation_page_number'] = None
    citations_gbif['citation_remarks'] = None
    
    # reorder
    citations_gbif = citations_gbif[['citation_target_id',
                                    'citation_target_type',
                                    'citation_reference_id',
                                    'citation_type',
                                    'citation_page_number',
                                    'citation_remarks']]
    
    # gbif structure for protocols
    protocol_gbif = (metodos[['id', 'descripcion']]
                     .rename(columns={'id': 'protocol_id', 'descripcion': 'protocol_type'}))
    
    return protocol_gbif, reference_gbif, citations_gbif

if __name__ == '__main__':
    metodos=getMetodos()
    protocol_gbif, reference_gbif, citations_gbif = gbif_references_for_methods(metodos)
    protocol_gbif.to_csv('protocol.csv', encoding='utf-8-sig', index=False)
    reference_gbif.to_csv('reference.csv', encoding='utf-8-sig', index=False)
    citations_gbif.to_csv('citations.csv', encoding='utf-8-sig', index=False)
