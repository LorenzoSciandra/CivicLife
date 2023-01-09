import React from 'react';
import '../App.css';
import {Button, Grid, Typography} from "@mui/material";
import basquiaPulito from "../imgs/logo_CivicLife.png"
import ManageAccountsIcon from '@mui/icons-material/ManageAccounts';
import {useNavigate} from "react-router-dom";

const Login = () => {
    const navigate = useNavigate();
    const testAdmin = false
    const loginWithFacebook = () => {
        console.log('click sul bottone FACEBOOK')
        const token = '123456'
        const email = 'email utente'
        if(testAdmin){
            navigate('/home', {state: {token: token, email: email, isAdmin: true}})
        }
        else{
            navigate('/home', {state: {token: token, email: email, isAdmin: false}})
        }
    }

    const loginWithGoogle = () => {
        console.log('click sul bottone GOOGLE')
        const token = '123456'
        const email = 'email utente'
        if(testAdmin){
            navigate('/home', {state: {token: token, email: email, isAdmin: true}})
        }
        else{
            navigate('/home', {state: {token: token, email: email, isAdmin: false}})
        }    }


    const loginWithGithub = () => {
        console.log('click sul bottone GITHUB')
        const token = '123456'
        const email = 'email utente'
        if(testAdmin){
            navigate('/home', {state: {token: token, email: email, isAdmin: true}})
        }
        else{
            navigate('/home', {state: {token: token, email: email, isAdmin: false}})
        }    }

    const loginWithTwitter = () => {
        console.log('click sul bottone TWITTER')
        const token = '123456'
        const email = 'email utente'
        if(testAdmin){
            navigate('/home', {state: {token: token, email: email, isAdmin: true}})
        }
        else{
            navigate('/home', {state: {token: token, email: email, isAdmin: false}})
        }    }

    const loginAsVisitor = () => {
        console.log('click sul bottone TWITTER')
        const token = '123456'
        const email = 'email utente'
        if(testAdmin){
            navigate('/home', {state: {token: token, email: email, isAdmin: true}})
        }
        else{
            navigate('/home', {state: {token: token, email: email, isAdmin: false}})
        }    }

    return (
        <><Grid container direction="row">
            <Grid item xs={12} display="flex" justifyContent="center" alignItems="center">
                <img src={basquiaPulito} width="320px" height="320px" alt={'civicLife Logo'}/>
            </Grid>
            <Grid item xs={12} display="flex" justifyContent="center" alignItems="center">
                <Typography style={{color: 'white', textAlign: 'center', fontSize: '4rem'}}>LOGIN</Typography>
            </Grid>
        </Grid><Grid container direction="row">
            <Grid item xs={3} display="flex" justifyContent="center" alignItems="center" style={{marginTop: '5px'}}>
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
            <Grid item xs={3} display="flex" justifyContent="center" alignItems="center">
                <Button
                    onClick={() => {
                        loginWithTwitter();
                    }}
                    style={{
                        borderRadius: 35,
                        backgroundColor: "#e7b928",
                        padding: "18px 36px",
                        fontSize: "25px"
                    }}
                    variant="contained">Twitter</Button>
            </Grid>
            <Grid item xs={3} display="flex" justifyContent="center" alignItems="center">
                <Button
                    onClick={() => {
                        loginWithGoogle();
                    }}
                    style={{
                        borderRadius: 35,
                        backgroundColor: "#ff0000",
                        padding: "18px 36px",
                        fontSize: "25px"
                    }}
                    variant="contained">GOOGLE</Button>
            </Grid>
            <Grid item xs={3} display="flex" justifyContent="center" alignItems="center">
                <Button
                    onClick={() => {
                        loginWithGithub();
                    }}
                    style={{
                        borderRadius: 35,
                        backgroundColor: "black",
                        padding: "18px 36px",
                        fontSize: "25px"
                    }}
                    variant="contained">GITHUB</Button>
            </Grid>
        </Grid>
            <Grid item xs={12} alignItems={'center'} display="flex">
                <Button
                    onClick={() => {
                        loginAsVisitor();
                    }}

                    startIcon={<ManageAccountsIcon fontSize='large'/>}
                    style={{
                        borderRadius: 35,
                        backgroundColor: "#808080",
                        padding: "18px 36px",
                        fontSize: "15px",
                        margin: 'auto',
                        position: 'fixed',
                        right: 20
                    }}
                    variant="contained">OSPITE</Button>
            </Grid>
        </>
    );
}

export default Login;

