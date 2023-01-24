import React, {useEffect, useState} from 'react';
import '../App.css';
import {Button, Grid, Typography} from "@mui/material";
import basquiaPulito from "../imgs/logo_CivicLife.png"
import {useNavigate} from "react-router-dom";
import {exchangeToken, isInstanceOfAuthError, TokenData} from "../APIs/OauthAPI";

const LoginChoser = () => {

    const [firstLoad, setFirstLoad] = useState(true);
    const [tokenData, setTokenData] = useState<TokenData| null>(null);

    const getTokenData = async (token_cifrato: string) => {
        const tokenResponse = await exchangeToken(token_cifrato);
        if (isInstanceOfAuthError(tokenResponse)) {
            navigate('/error', {state: {error: tokenResponse}})
        } else {
            if (tokenResponse) {
                setTokenData(tokenResponse)
                navigate('/home', {state: {tokenData: tokenResponse}})
            } else {
                console.log('error')
            }

        }
    }

    useEffect(() => {
        if (firstLoad) {
                if (window.location.href.includes('token=') && tokenData === null) {
                    const token_cifrato = window.location.href.split("token=")[1].toString()
                    if (token_cifrato !== "") {
                        getTokenData(token_cifrato)

                    }
                } else {
                    console.log("token non presente")
                }
            }
        setFirstLoad(false)
    }, [])

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
                        variant="contained">Ospite</Button>
                </Grid>
            </Grid>
        </>

    );
}

export default LoginChoser;

