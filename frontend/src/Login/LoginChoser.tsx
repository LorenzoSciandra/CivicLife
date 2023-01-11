import React, {useEffect} from 'react';
import '../App.css';
import {Button, Grid, Typography} from "@mui/material";
import basquiaPulito from "../imgs/logo_CivicLife.png"
import {useNavigate} from "react-router-dom";

type LoginChoserPropsType = {
    isMobile: boolean
}

const LoginChoser = (props: LoginChoserPropsType) => {
    const navigate = useNavigate();
    const loginWithService = () => {
        window.location.assign('http://localhost:8080/login')
    }


    const loginAsVisitor = () => {
        navigate('/home', {state: {isVisitor: true}})
    }

    return (
        <>
            <Grid container>
                <Grid item xs={12} display="flex" justifyContent="center" alignItems="center">
                    <img src={basquiaPulito} width="320px" height="320px" alt={'civicLife Logo'}/>
                </Grid>
                <Grid item xs={12} display="flex" justifyContent="center" alignItems="center">
                    <Typography style={{color: 'white', textAlign: 'center', fontSize: '4rem'}}>LOGIN</Typography>
                </Grid>
            </Grid>
            <Grid container >
                <Grid item xs={6} display="flex" justifyContent="center" alignItems="center">
                    <Button
                        onClick={loginWithService}
                        style={{
                            position: 'absolute',
                            marginBottom: '100px',
                            borderRadius: 35,
                            backgroundColor: "#21b6ae",
                            padding: "18px 36px",
                            fontSize: "25px"
                        }}
                        variant="contained">Accedi</Button>
                </Grid>
                <Grid item xs={6} display="flex" justifyContent="center" alignItems="center">
                    <Button
                        onClick={loginAsVisitor}
                        style={{
                            position: 'absolute',
                            marginBottom: '100px',
                            borderRadius: 35,
                            backgroundColor: "#feac0d",
                            padding: "18px 36px",
                            fontSize: "25px"
                        }}
                        variant="contained">OSpite</Button>
                </Grid>
            </Grid>
        </>

    );
}

export default LoginChoser;

