import {
    AppBar,
    Button,
    Card,
    CardActionArea,
    CardContent,
    CardMedia,
    Divider,
    Grid,
    IconButton,
    Typography
} from "@mui/material";
import React, {useEffect, useState} from "react";
import '../App.css';
import vote from "../imgs/vote.png"
import personalData from "../imgs/personaldata.png"
import iniziative from "../imgs/iniziative.png"
import "@fontsource/ubuntu-mono";
import {useLocation, useNavigate} from "react-router-dom";
import {exchangeToken, isInstanceOfAuthError, logoutUser, TokenData} from "../APIs/OauthAPI";
import type {User} from "../APIs/UsersAPI";
import {getLoggedUser, UserStatus, UserStatusColor} from "../APIs/UsersAPI";
import Box from "@mui/material/Box";
import Toolbar from "@mui/material/Toolbar";
import HomeIcon from '@mui/icons-material/Home';
import AdminPanelSettingsIcon from '@mui/icons-material/AdminPanelSettings';
import CircleIcon from '@mui/icons-material/Circle';

const MainPage = () => {

    const location = useLocation();
    const isVisitor = location.state?.isVisitor
    const navigate = useNavigate();
    const [tokenData, setTokenData] = useState<TokenData | null>(null);
    const [firstLoad, setFirstLoad] = useState(true);
    const [user, setUser] = useState<User | null>(null);

    const getTokenData = async (token_cifrato: string) => {
        const tokenResponse = await exchangeToken(token_cifrato);
        if (isInstanceOfAuthError(tokenResponse)) {
            navigate('/error', {state: {error: tokenResponse}})
        } else {
            if (tokenResponse) {
                setTokenData(tokenResponse)
            } else {
                console.log('error')
            }

        }
    }

    useEffect(() => {
        if (firstLoad) {
            if (isVisitor === undefined) {
                if (window.location.href.includes('token=') && tokenData === null) {
                    const token_cifrato = window.location.href.split("token=")[1].toString()
                    if (token_cifrato !== "") {
                        getTokenData(token_cifrato)

                    }
                } else {
                    console.log("token non presente")
                    returnToLogin()
                }
            } else {
                console.log('utente visitatore')
            }
        }
        setFirstLoad(false)
    })

    const getUserData = async () => {
        if (tokenData !== null) {
            const response = await getLoggedUser(tokenData)
            if (isInstanceOfAuthError(response)) {
                navigate('/error', {state: {error: response}})
            } else {
                setUser(response)
            }
        }
    }

    useEffect(() => {
        if (tokenData !== null) {
            getUserData()
        }
    }, [tokenData])

    const returnToLogin = () => {
        return (navigate("/"))
    }

    const login = () => {
        window.location.assign('http://localhost:8080/login')
    }

    const logout = async () => {
        if (tokenData !== null) {
            const response = await logoutUser(tokenData)
            if (typeof response === 'boolean') {
                if (response) {
                    navigate('/')
                } else {
                    console.log('error')
                }
            } else {
                navigate('/error', {state: {error: response}})
            }
        }
    }

    const goToVotations = () => {

        // if (isAdmin) {
        //     navigate('/votationsAdmin', {state: {token: token, email: email, isAdmin: isAdmin}})
        // } else {
        //     if (isVisitor) {
        //         navigate('/votations', {state: {isVisitor: isVisitor}})
        //     } else {
        //         navigate('/votations', {state: {token: token, email: email, isAdmin: isAdmin}})
        //     }
        // }
    }

    const goToData = () => {
        console.log('lo porto ai dati')
        if (user !== null) {
            console.log('lo porto ai dati di ', user)
            if (user.admin) {
                navigate('/usersAdmin', {state: {token: tokenData}})
            } else {
                navigate('/myData', {state: {token: tokenData}})
            }
        }

    }

    const goToInitiatives = () => {
            if((user && user.status!==UserStatus.BANNED)|| isVisitor){
                navigate('/initiatives', {state: {token: tokenData, isVisitor: isVisitor, user: user}})
            }
    }

    return (
        <>
            <Grid container direction="row" spacing={3}>
                <Grid item xs={12} justifyContent="center" alignItems="center">
                    <Box sx={{flexGrow: 1}}>
                        <AppBar position="fixed" sx={{backgroundColor: '#3d4347'}}>
                            <Toolbar>
                                <IconButton
                                    disabled={true}
                                    size="large"
                                    edge="start"
                                    aria-label="menu"
                                    sx={{mr: 2}}
                                >
                                    {user && user.admin ? <AdminPanelSettingsIcon sx={{color:'#feac0d'}}/>:
                                        <><HomeIcon sx={{color: 'white'}}/><CircleIcon
                                            sx={{color: user? UserStatusColor[user.status]: null}}/></>
                                    }
                                </IconButton>
                                {user && !isVisitor ?
                                    <Typography variant="h6" component="div" sx={{flexGrow: 1}}
                                                style={{
                                                    justifyContent: 'center',
                                                    color: 'white',
                                                    textAlign: 'center',
                                                    fontSize: '1.8rem',
                                                }}>BENVENUTO {user.name} {user.surname}
                                    </Typography>
                                    : <Typography variant="h6" component="div" sx={{flexGrow: 1}}
                                                  style={{color: 'white', textAlign: 'center', fontSize: '1.2rem'}}>BENVENUTO
                                        OSPITE
                                    </Typography>}
                                <Button
                                        onClick={isVisitor ? login : logout}
                                        style={{
                                        color:'white',
                                        backgroundColor: isVisitor? "green" : "red",
                                    }}>

                                        {isVisitor? "login" : "logout"}
                                    </Button>
                            </Toolbar>
                        </AppBar>
                    </Box>
                </Grid>
                <Grid item xs={6} display="flex" justifyContent="center" alignItems="center">
                    <Card sx={{width: '60%',"&:hover": {
                            background: "#d7d7d7"
                        }}} onClick={goToVotations}>
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
                                            {user && user.admin ? 'Inserisci Votazioni' : 'Votazioni'}
                                        </Typography>
                                    </CardContent>
                                </Grid>
                            </Grid>
                        </CardActionArea>
                    </Card>
                </Grid>
                <Grid item xs={6} display="flex" justifyContent="center" alignItems="center">
                    <Card sx={{width: '60%',"&:hover": {
                            background: "#d7d7d7"
                        }}} onClick={goToInitiatives}>
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
                                            {user && user.admin ? 'Gestisci iniziative' : 'Iniziative'}
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
                            sx={{width: '30%',"&:hover": {
                                    background: "#d7d7d7"
                                }}}>
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
                                                {user && user.admin ? 'Modera utenti' : 'I tuoi Dati'}
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