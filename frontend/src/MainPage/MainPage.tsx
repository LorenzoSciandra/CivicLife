import {Card, CardActionArea, CardContent, CardMedia, Divider, Grid, Typography} from "@mui/material";
import React, {useEffect, useState} from "react";
import '../App.css';
import vote from "../imgs/vote.png"
import personalData from "../imgs/personaldata.png"
import iniziative from "../imgs/iniziative.png"
import "@fontsource/ubuntu-mono";
import {useLocation, useNavigate} from "react-router-dom";
import {exchangeToken, TokenData} from "../APIs/OauthAPI";
import {getLoggedUser} from "../APIs/UsersAPI";
import type {User} from "../APIs/UsersAPI";

const MainPage = () => {

    const location = useLocation();
    const isAdmin = location.state?.isAdmin;
    const email = location.state?.email;
    const token = location.state?.token;
    const isVisitor = location.state?.isVisitor;
    const navigate = useNavigate();

    const [tokenData, setTokenData] = useState<TokenData | null>(null);
    const [firstLoad, setFirstLoad] =useState(true);
    const [user, setUser] = useState<User| null>(null);

    const getTokenData = async (token_cifrato: string) => {
        const tokenData = await exchangeToken(token_cifrato);
        console.log('tokenData', tokenData)
        setTokenData(tokenData)
    }

    useEffect(() => {
        if(firstLoad) {
            if(isVisitor===undefined){
                if (window.location.href.includes('token=') && tokenData === null) {
                    const token_cifrato = window.location.href.split("token=")[1].toString()
                    if (token_cifrato !== "") {
                        getTokenData(token_cifrato)

                    }
                } else {
                    console.log("token non presente")
                    returnToLogin()
                }
            }
            else{
                console.log('utente visitatore')
            }
        }
        setFirstLoad(false)
    })

    const getUserData = async () => {
        if(tokenData !== null){
            const user= await getLoggedUser(tokenData)
            setUser(user)
        }
    }

    useEffect(() => {
        if(tokenData !== null){
            console.log('qui')
            getUserData()
        }
    }, [tokenData])

    const returnToLogin = () => {
        return (navigate("/"))
    }

    const goToVotations = () => {

        if (isAdmin) {
            navigate('/votationsAdmin', {state: {token: token, email: email, isAdmin: isAdmin}})
        } else {
            if (isVisitor) {
                navigate('/votations', {state: {isVisitor: isVisitor}})
            } else {
                navigate('/votations', {state: {token: token, email: email, isAdmin: isAdmin}})
            }
        }
    }

    const goToData = () => {
        console.log('lo porto ai dati')
        if(user !== null){
            console.log('lo porto ai dati di ', user)
            if (user.admin) {
                navigate('/usersAdmin', {state: {token: tokenData, user: user}})
            } else {
                navigate('/myData', {state: {token: tokenData, user: user}})
            }
        }

    }

    const goToInitiatives = () => {
        navigate('/initiatives', {state: {token: token, email: email, isAdmin: isAdmin}})
    }

    return (
        <>
            <Grid container direction="row" spacing={3}>
                <Grid item xs={12} display="flex" justifyContent="center" alignItems="center" sx={{
                }}>
                    {user && !isVisitor ?  <Typography style={{color: 'white', textAlign: 'center', fontSize: '3rem'}}>BENVENUTO {user.name} {user.surname}</Typography>
                    : <Typography style={{color: 'white', textAlign: 'center', fontSize: '3rem'}}>BENVENUTO OSPITE</Typography>}

                </Grid>

                <Grid item xs={6} display="flex" justifyContent="center" alignItems="center">
                    <Card sx={{width: '60%'}} onClick={goToVotations}>
                        <CardActionArea>
                            <Grid container direction="column">
                                <Grid item display="flex" justifyContent="center" alignItems="center"
                                      style={{backgroundColor: '#ff5d55'}}>
                                    <CardMedia
                                        component="img"
                                        image={vote}
                                        sx={{
                                            width: '45%',
                                        }}
                                        alt="vote"/>
                                </Grid>
                                <Divider style={{marginTop: '5px'}}/>
                                <Grid item display="flex" justifyContent="center" alignItems="center">
                                    <CardContent>
                                        <Typography gutterBottom variant="h5" component="div">
                                            {isAdmin ? 'Inserisci Votazioni' : 'Votazioni'}
                                        </Typography>
                                    </CardContent>
                                </Grid>
                            </Grid>
                        </CardActionArea>
                    </Card>
                </Grid>
                <Grid item xs={6} display="flex" justifyContent="center" alignItems="center">
                    <Card sx={{width: '60%'}} onClick={goToInitiatives}>
                        <CardActionArea>
                            <Grid container direction="column">
                                <Grid item display="flex" justifyContent="center" alignItems="center"
                                      style={{backgroundColor: '#f1f6be'}}>
                                    <CardMedia
                                        component="img"
                                        image={iniziative}
                                        sx={{
                                            width: '70%',
                                        }}
                                        alt="iniziative"/>
                                </Grid>
                                <Divider style={{marginTop: '5px'}}/>
                                <Grid item display="flex" justifyContent="center" alignItems="center">
                                    <CardContent>
                                        <Typography gutterBottom variant="h5" component="div">
                                            {isAdmin ? 'Gestisci iniziative' : 'Iniziative'}
                                        </Typography>
                                    </CardContent>
                                </Grid>
                            </Grid>
                        </CardActionArea>
                    </Card>
                </Grid>
                {isVisitor ? null :
                    <Grid item xs={12} display="flex" justifyContent="center" alignItems="center">
                    <Card
                        onClick={goToData}
                        sx={{width: '30%'}}>
                        <CardActionArea>
                            <Grid container direction="column">
                                <Grid item display="flex" justifyContent="center" alignItems="center"
                                      style={{backgroundColor: '#d4e3fc'}}>
                                    <CardMedia
                                        component="img"
                                        image={personalData}
                                        sx={{
                                            width: '60%',
                                        }}
                                        alt="personalData"/>
                                </Grid>
                                <Divider style={{marginTop: '5px'}}/>
                                <Grid item display="flex" justifyContent="center" alignItems="center">
                                    <CardContent>
                                        <Typography gutterBottom variant="h5" component="div">
                                            {isAdmin ? 'Modera utenti' : 'I tuoi Dati'}
                                        </Typography>
                                    </CardContent>
                                </Grid>
                            </Grid>
                        </CardActionArea>
                    </Card>
                </Grid>
                }

            </Grid>
        </>
    );

}

export default MainPage;