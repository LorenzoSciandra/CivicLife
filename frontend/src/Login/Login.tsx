import React from 'react';
import '../App.css';
import {Button, Grid, Typography} from "@mui/material";
import basquiaPulito from "../imgs/logo_CivicLife.png"
import ManageAccountsIcon from '@mui/icons-material/ManageAccounts';
const Login = () => {

  const loginWithFacebook=()=>{
    console.log('click sul bottone FACEBOOK')
  }

  return (
      <><Grid container direction="row">
        <Grid item xs={12} display="flex" justifyContent="center" alignItems="center">
          <img src={basquiaPulito} width="320px" height="320px" alt={'civicLife Logo'}/>
        </Grid>
        <Grid item xs={12} display="flex" justifyContent="center" alignItems="center">
          <Typography style={{color: 'white', textAlign: 'center', fontSize: '4rem'}}>LOGIN</Typography>
        </Grid>
      </Grid><Grid container direction="row">
        <Grid item xs={4} display="flex" justifyContent="center" alignItems="center" style={{marginTop: '5px'}}>
          <Button
              onClick={() => {
                loginWithFacebook();
              }}
              style={{
                borderRadius: 35,
                backgroundColor: "#21b6ae",
                padding: "18px 36px",
                fontSize: "25px"
              }}
              variant="contained">FACEBOOK</Button>
        </Grid>
        <Grid item xs={4} display="flex" justifyContent="center" alignItems="center">
          <Button style={{
            borderRadius: 35,
            backgroundColor: "#e7b928",
            padding: "18px 36px",
            fontSize: "25px"
          }}
                  variant="contained">SPID</Button>
        </Grid>
        <Grid item xs={4} display="flex" justifyContent="center" alignItems="center">
          <Button style={{
            borderRadius: 35,
            backgroundColor: "#ff0000",
            padding: "18px 36px",
            fontSize: "25px"
          }}
                  variant="contained">GOOGLE</Button>
        </Grid>
      </Grid>
              <Grid item xs={12} alignItems={'center'} display="flex">
                  <Typography style={{color: '#feac0d', fontSize: '1.2rem', marginLeft:'15px', bottom:0 }}>CivicLife</Typography>

                  <Button startIcon={<ManageAccountsIcon fontSize='large'/>} style={{
                      borderRadius: 35,
                      backgroundColor: "#808080",
                      padding: "18px 36px",
                      fontSize: "15px",
                      margin:'auto',
                      position:'fixed',
                      right:20
                  }}
                          variant="contained">ADMIN</Button>
              </Grid>
      </>
  );
}

export default Login;

