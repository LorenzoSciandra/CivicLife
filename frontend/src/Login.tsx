import React from 'react';
import './App.css';
import {Button, createTheme, Grid, Typography} from "@mui/material";
import basquiaPulito from "./imgs/basquiaPulito.png"

const Login = () => {

  const loginWithFacebook=()=>{
    console.log('click sul bottone FACEBOOK')
  }

  return (
      <Grid container className="App-header">
        <Grid container direction="row">
          <Grid item xs={12} display="flex" justifyContent="center" alignItems="center">
            <img src={basquiaPulito} width="320px" height="320px" alt={'civicLife Logo'}/>
          </Grid>
          <Grid item xs={12} display="flex" justifyContent="center" alignItems="center">
            <Typography style={{color: 'white', textAlign: 'center', fontSize: '2rem'}}>CivicLife</Typography>
          </Grid>
        </Grid>
        <Grid container direction="row">
          <Grid item xs={4} display="flex" justifyContent="center" alignItems="center" style={{marginTop:'5px'}}>
            <Button
                onClick={()=>{loginWithFacebook()}}
                style={{
              borderRadius: 35,
              backgroundColor: "#21b6ae",
              padding: "18px 36px",
              fontSize: "18px"
            }}
                    variant="contained">FACEBOOK</Button>
          </Grid>
          <Grid item xs={4} display="flex" justifyContent="center" alignItems="center">
            <Button style={{
              borderRadius: 35,
              backgroundColor: "#e7b928",
              padding: "18px 36px",
              fontSize: "18px"
            }}
                    variant="contained">SPID</Button>
          </Grid>
          <Grid item xs={4} display="flex" justifyContent="center" alignItems="center">
            <Button style={{
              borderRadius: 35,
              backgroundColor: "#ff0000",
              padding: "18px 36px",
              fontSize: "18px"
            }}
                    variant="contained">GOOGLE</Button>
          </Grid>
        </Grid>
      </Grid>
  );
}

export default Login;

