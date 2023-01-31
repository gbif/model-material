from ploomber import with_env
from ploomber.clients import SQLAlchemyClient


@with_env
def get_client(env):
    db = env.db
    return SQLAlchemyClient(
        f"mariadb+mariadbconnector://{db.user}:{db.password}@{db.host}/{db.schema}"
    )
