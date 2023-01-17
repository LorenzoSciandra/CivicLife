import {Button, Grid, Typography} from "@mui/material";
import React from "react";

type AuthRequiredPropsType = {
    authFor: 'Bonus' | 'Vaccini',
    authFunction: () => void
}
const AuthRequired = (props: AuthRequiredPropsType) => {
    const authFor = props.authFor
    const authFunction = props.authFunction
    return (

            <Grid container sx={{position:'fixed', top: '40%'}}>
                <Grid item xs={12} display="flex" justifyContent="center" alignItems="center">
                    <Typography variant="h4" sx={{marginBottom: 2, paddingLeft: '10px', paddingRight:'10px', textAlign: 'center',}}>Per visualizzare i tuoi {authFor} devi fornire la tua
                        autorizzazione</Typography>

                </Grid>
                <Grid item xs={12} display="flex" justifyContent="center" alignItems="center">

                    <Button style={{
                        borderRadius: 35,
                        backgroundColor: "#92d36e",
                        padding: "10px 20px",
                        fontSize: "18px"
                    }}
                            variant="contained"
                            onClick={authFunction}>Autorizza</Button>
                </Grid>
            </Grid>
    );
}

export default AuthRequired;