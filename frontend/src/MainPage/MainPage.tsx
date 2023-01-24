import {
    AppBar,
    Button,
    Card,
    CardActionArea,
    CardContent,
    CardMedia,
    Grid,
    IconButton,
    Typography
} from "@mui/material";
import React, {useEffect, useState} from "react";
import '../App.css';
import vote from "../imgs/voteResize.png"
import iniziative from "../imgs/iniziativeResize.png"
import personaldata from "../imgs/personaldataResize.png"
import "@fontsource/ubuntu-mono";
import {useLocation, useNavigate} from "react-router-dom";
import {isInstanceOfAuthError, logoutUser} from "../APIs/OauthAPI";
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
    const tokenData = location.state?.tokenData
    const navigate = useNavigate();
    const [firstLoad, setFirstLoad] = useState(true);
    const [user, setUser] = useState<User | null>(null);

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
        if (tokenData !== null && firstLoad) {
            getUserData()
        }
        setFirstLoad(false)
    }, [])

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
        if (user && user.admin) {
            navigate('/votationsAdmin', {state: {token: tokenData}})
        } else {
            navigate('/votations', {state: {token: tokenData, isVisitor: isVisitor, user: user}})
        }
    }

    const goToData = () => {
        if (user !== null) {
            if (user.admin) {
                navigate('/usersAdmin', {state: {token: tokenData}})
            } else {
                navigate('/myData', {state: {token: tokenData}})
            }
        }

    }

    const goToInitiatives = () => {
        if ((user && user.status !== UserStatus.BANNED) || isVisitor) {
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
                                    {user && user.admin ? <AdminPanelSettingsIcon sx={{color: '#feac0d'}}/> :
                                        <><HomeIcon sx={{color: 'white'}}/><CircleIcon
                                            sx={{color: user ? UserStatusColor[user.status] : null}}/></>
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
                                        color: 'white',
                                        backgroundColor: isVisitor ? "green" : "red",
                                    }}>

                                    {isVisitor ? "login" : "logout"}
                                </Button>
                            </Toolbar>
                        </AppBar>
                    </Box>
                </Grid>
            </Grid>

            <Grid xs={8} container direction="column" display="flex" justifyContent="space-around" alignItems="stretch"
                  sx={{marginTop: '80px'}}>
                <Card sx={{
                    maxHeight: '300px', margin: '20px', "&:hover": {
                        background: "#d7d7d7"
                    }
                }} onClick={goToVotations}>
                    <Grid item display="flex" justifyContent="center" alignItems="center" sx={{backgroundColor:'#ff5d55'}}>
                    <CardMedia
                        sx={{backgroundColor: '#ff5d55', maxWidth: 200, maxHeight: 200}}
                        component="img"
                        alt="vote img"
                        height="200"
                        image={vote}
                    />
                    </Grid>
                    <CardActionArea>
                        <Grid container direction="column">
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
                <Card sx={{
                    maxHeight: '300px', margin: '20px', "&:hover": {
                        background: "#d7d7d7"
                    }
                }} onClick={goToInitiatives}>
                        <Grid item display="flex" justifyContent="center" alignItems="center" sx={{backgroundColor:'#f1f6be'}}>
                            <CardMedia
                                sx={{ maxWidth: 320, maxHeight:200, backgroundColor:'#f1f6be' }}
                                component="img"
                                alt="vote img"
                                height="200"
                                image={iniziative}
                            />
                        </Grid>
                        <CardActionArea>
                            <Grid item display="flex" justifyContent="center" alignItems="center">
                                <CardContent>
                                    <Typography gutterBottom variant="h5" component="div">
                                        {user && user.admin ? 'Gestisci iniziative' : 'Iniziative'}
                                    </Typography>
                                </CardContent>
                            </Grid>

                        </CardActionArea>
                </Card>
                {isVisitor ? null :
                    <Card
                        onClick={goToData}
                        sx={{
                            maxHeight: '300px', margin: '20px', "&:hover": {
                                background: "#d7d7d7"
                            }
                        }}>
                        <Grid item display="flex" justifyContent="center" alignItems="center" sx={{backgroundColor:'#d4e3fc'}}>
                            <CardMedia
                                sx={{backgroundColor: '#d4e3fc', maxWidth: 269, maxHeight:200}}
                                component="img"
                                alt="vote img"
                                height="200"
                                image={personaldata}
                            />
                        </Grid>


                        <CardActionArea>
                            <Grid item display="flex" justifyContent="center" alignItems="center">
                                <CardContent>
                                    <Typography gutterBottom variant="h5" component="div">
                                        {user && user.admin ? 'Modera utenti' : 'I tuoi Dati'}
                                    </Typography>
                                </CardContent>
                            </Grid>
                        </CardActionArea>
                    </Card>
                }
            </Grid>
            {/*<Grid item xs={12} alignContent='center'>*/}
            {/*    <Card sx={{*/}
            {/*        width: '60%', "&:hover": {*/}
            {/*            background: "#d7d7d7"*/}
            {/*        }*/}
            {/*    }} onClick={goToVotations}>*/}
            {/*        <CardActionArea>*/}
            {/*            <Grid container direction="column">*/}
            {/*                <Grid item display="flex" justifyContent="center" alignItems="center"*/}
            {/*                      style={{backgroundColor: '#ff5d55'}}>*/}
            {/*                    <CardMedia*/}
            {/*                        component="img"*/}
            {/*                        image={vote}*/}
            {/*                        sx={{*/}
            {/*                            width: '45%',*/}
            {/*                        }}*/}
            {/*                        alt="vote"/>*/}
            {/*                </Grid>*/}
            {/*                <Divider style={{marginTop: '5px'}}/>*/}
            {/*                <Grid item display="flex" justifyContent="center" alignItems="center">*/}
            {/*                    <CardContent>*/}
            {/*                        <Typography gutterBottom variant="h5" component="div">*/}
            {/*                            {user && user.admin ? 'Inserisci Votazioni' : 'Votazioni'}*/}
            {/*                        </Typography>*/}
            {/*                    </CardContent>*/}
            {/*                </Grid>*/}
            {/*            </Grid>*/}
            {/*        </CardActionArea>*/}
            {/*    </Card>*/}
            {/*</Grid>*/}
            {/*<Grid item xs={12} alignContent="center">*/}
            {/*    <Card sx={{*/}
            {/*        width: '60%', "&:hover": {*/}
            {/*            background: "#d7d7d7"*/}
            {/*        }*/}
            {/*    }} onClick={goToInitiatives}>*/}
            {/*        <CardActionArea>*/}
            {/*            <Grid container direction="column">*/}
            {/*                <Grid item display="flex" justifyContent="center" alignItems="center"*/}
            {/*                      style={{backgroundColor: '#f1f6be'}}>*/}
            {/*                    <CardMedia*/}
            {/*                        component="img"*/}
            {/*                        image={iniziative}*/}
            {/*                        sx={{*/}
            {/*                            width: '70%',*/}
            {/*                        }}*/}
            {/*                        alt="iniziative"/>*/}
            {/*                </Grid>*/}
            {/*                <Divider style={{marginTop: '5px'}}/>*/}
            {/*                <Grid item display="flex" justifyContent="center" alignItems="center">*/}
            {/*                    <CardContent>*/}
            {/*                        <Typography gutterBottom variant="h5" component="div">*/}
            {/*                            {user && user.admin ? 'Gestisci iniziative' : 'Iniziative'}*/}
            {/*                        </Typography>*/}
            {/*                    </CardContent>*/}
            {/*                </Grid>*/}
            {/*            </Grid>*/}
            {/*        </CardActionArea>*/}
            {/*    </Card>*/}
            {/*</Grid>*/}
            {/*{isVisitor ? null :*/}
            {/*    <Grid item xs={12} alignContent='center'>*/}
            {/*        <Card*/}
            {/*            onClick={goToData}*/}
            {/*            sx={{*/}
            {/*                width: '60%', "&:hover": {*/}
            {/*                    background: "#d7d7d7"*/}
            {/*                }*/}
            {/*            }}>*/}
            {/*            <CardActionArea>*/}
            {/*                <Grid container direction="column">*/}
            {/*                    <Grid item display="flex" justifyContent="center" alignItems="center"*/}
            {/*                          style={{backgroundColor: '#d4e3fc'}}>*/}
            {/*                        <CardMedia*/}
            {/*                            component="img"*/}
            {/*                            image={personalData}*/}
            {/*                            sx={{*/}
            {/*                                width: '60%',*/}
            {/*                            }}*/}
            {/*                            alt="personalData"/>*/}
            {/*                    </Grid>*/}
            {/*                    <Divider style={{marginTop: '5px'}}/>*/}
            {/*                    <Grid item display="flex" justifyContent="center" alignItems="center">*/}
            {/*                        <CardContent>*/}
            {/*                            <Typography gutterBottom variant="h5" component="div">*/}
            {/*                                {user && user.admin ? 'Modera utenti' : 'I tuoi Dati'}*/}
            {/*                            </Typography>*/}
            {/*                        </CardContent>*/}
            {/*                    </Grid>*/}
            {/*                </Grid>*/}
            {/*            </CardActionArea>*/}
            {/*        </Card>*/}
            {/*    </Grid>*/}
            {/*}*/}
        </>
    );
}

export default MainPage;