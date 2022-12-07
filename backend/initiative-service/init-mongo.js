db.createUser(
    {
        user : "taass",
        pwd : "taass",
        roles : [
            {
                role : "readWrite",
                db : "Iniziative"
            }
        ]
    }
)