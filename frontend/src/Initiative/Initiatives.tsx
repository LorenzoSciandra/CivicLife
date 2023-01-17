import {AppBar, Button, Divider, Grid, IconButton, ToggleButton, ToggleButtonGroup, Typography,} from "@mui/material";
import '../App.css'
import React, {useEffect, useState} from "react";
import ListItem from '@mui/material/ListItem';
import ListItemButton from "@mui/material/ListItemButton";
import List from "@mui/material/List";
import {useLocation, useNavigate} from "react-router-dom";
import {getLoggedUser, isInstanceOfUser, User} from "../APIs/UsersAPI";
import {getAllInitiatives} from "../APIs/InitiativeAPI";
import Box from "@mui/material/Box";
import Toolbar from "@mui/material/Toolbar";
import ArrowBackIcon from "@mui/icons-material/ArrowBack";
import {isInstanceOfAuthError, logoutUser} from "../APIs/OauthAPI";

type ButtonStyleType = {
    background: string,
    color: string
}
const activeButtonColor: ButtonStyleType = {
    background: '#feac0d',
    color: '#ffffff'
}
const inactiveButtonColor: ButtonStyleType = {
    background: '#ffffff',
    color: '#feac0d'
}

const Initiatives = () => {
    const navigate = useNavigate()
    const location = useLocation()
    const tokenData = location.state.token
    const buttons = ['Tutte', 'Mie', 'Sottoscritte']
    const [allInitiativesList, setAllInitiativesList] = useState<any[] | null>(null)
    const [myInitiativesList, setMyInitiativesList] = useState<any[]>([])
    const [subcribedInitiativesList, setSubscribedInitiativesList] = useState<any[]>(['sottoscritta1', 'sottoscritta2'])
    const [showingList, setShowingList] = useState<any[]>([])
    const [showModal, setShowModal] = useState(false)
    const [activeButton, setActiveButton] = useState(buttons[0]);
    const [clickedInitiative, setClickedInitiative] = useState(null)
    const [loggedUser, setLoggedUser] = useState<User | null>(null)
    const [firstButton, setFirstButton] = useState<ButtonStyleType>(activeButtonColor)
    const [secondButton, setSecondButton] = useState<ButtonStyleType>(inactiveButtonColor)
    const [thirdButton, setThirdButton] = useState<ButtonStyleType>(inactiveButtonColor)

    const getUser = async () => {
        const response = await getLoggedUser(tokenData)
        if (isInstanceOfUser(response)) {
            setLoggedUser(response)
        } else {
            navigate('/error', {state: {error: response}})
        }
    }

    const getInitiatives = async () => {
        const response = await getAllInitiatives()
        if(isInstanceOfAuthError(response)) {
            navigate('/error', {state: {error: response}})
        }
        else{
            setAllInitiativesList(response)
        }
    }

    useEffect(() => {
        if (loggedUser === null) {
            getUser()

        }
        if (allInitiativesList === null) {
            getInitiatives()
        }
    })

    const handleInitiativeDetailsOpen = (value: any) => {
        navigate('/initiativeDetails', {
            state: {
                token: location.state?.token,
                email: location.state?.email,
                isAdmin: location.state?.isAdmin,
                initiative: value
            }
        })
    }

    const handleCreateInitiative = () => {
        navigate('/createInitiative', {
            state: {
                token: tokenData,
            }
        })
    }

    const handleChange = (event: React.MouseEvent<HTMLElement>, newActiveButton: string,) => {
        if (newActiveButton === buttons[0]) {
            setFirstButton(activeButtonColor)
            setSecondButton(inactiveButtonColor)
            setThirdButton(inactiveButtonColor)
        } else if (newActiveButton === buttons[1]) {
            setFirstButton(inactiveButtonColor)
            setSecondButton(activeButtonColor)
            setThirdButton(inactiveButtonColor)
        } else if (newActiveButton === buttons[2]) {
            setFirstButton(inactiveButtonColor)
            setSecondButton(inactiveButtonColor)
            setThirdButton(activeButtonColor)
        }
        setActiveButton(newActiveButton);
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

    const goBack = () => {
        navigate(-1)
    }

    return (
        <>
            <Grid container direction="row" spacing={2}>
                <Grid item xs={12} justifyContent="center" alignItems="center" >
                    <Box sx={{ flexGrow: 1 }}>
                        <AppBar position="fixed" sx={{backgroundColor:'#3d4347' }}>
                            <Toolbar>
                                <IconButton
                                    size="small"
                                    edge="start"
                                    aria-label="menu"
                                    sx={{ mr: 2}}
                                >
                                    <ArrowBackIcon onClick={goBack} sx={{fontSize: '3rem', color: 'white'}}/>
                                </IconButton>
                                <Typography variant="h6" component="div" sx={{flexGrow: 1}}
                                            style={{
                                                justifyContent: 'center',
                                                color: 'white',
                                                textAlign: 'center',
                                                fontSize: '1.8rem',
                                            }}>I tuoi dati
                                </Typography>
                                <Button sx={{color: 'white', backgroundColor: 'red'}}
                                        onClick={logout}>
                                    logout
                                </Button>
                            </Toolbar>
                        </AppBar>
                    </Box>
                </Grid>
                {loggedUser && !loggedUser.admin ?
                    <Grid item xs={12} display="flex" justifyContent="center" alignItems="center"
                          sx={{position: 'fixed', top: '8%', margin: 'auto', width: '100%'}}>
                        <ToggleButtonGroup value={activeButton} exclusive onChange={handleChange} aria-label="Platform">
                            <ToggleButton style={{
                                backgroundColor: firstButton.background,
                                color: firstButton.color,
                                borderColor: '#000000',
                                fontSize: '25px'
                            }} value={buttons[0]}>{buttons[0]}</ToggleButton>
                            <ToggleButton style={{
                                backgroundColor: secondButton.background,
                                color: secondButton.color,
                                borderColor: '#000000',
                                fontSize: '25px'
                            }} value={buttons[1]}>{buttons[1]}</ToggleButton>
                            <ToggleButton style={{
                                backgroundColor: thirdButton.background,
                                color: thirdButton.color,
                                borderColor: '#000000',
                                fontSize: '25px'
                            }} value={buttons[2]}>{buttons[2]}</ToggleButton>
                        </ToggleButtonGroup>
                    </Grid>
                    :
                    <Grid item xs={12} display="flex" justifyContent="center" alignItems="center">
                        <Typography style={{
                            color: '#feac0d', textAlign: 'center', fontSize: '3rem', position: 'fixed',
                            margin: "auto",
                            top: 10,
                        }}>Amministrazione iniziative</Typography>
                    </Grid>}

                <Grid item xs={12} display="flex" justifyContent="center" alignItems="center">
                    <List sx={{
                        width: '85%',
                        overflow: 'auto',
                        maxHeight: 550,
                        position: 'fixed',
                        top: '20%',
                        bottom: '1%'
                    }}>
                        {showingList.map((value, index) => {
                            return (
                                <><
                                    ListItem key={index}>
                                    <ListItemButton
                                        onClick={() => handleInitiativeDetailsOpen(value)}>{value}</ListItemButton>
                                </ListItem>
                                    <Divider color='white'/>
                                </>
                            );
                        })}
                    </List>
                </Grid>
                <Grid item xs={12} display="flex" justifyContent='center' alignItems="right">
                    {
                        loggedUser && loggedUser.admin ?
                            <Button style={{
                                position: 'fixed',
                                bottom: 20,
                                borderRadius: 35,
                                backgroundColor: "red",
                                padding: "18px 36px",
                                fontSize: "15px"
                            }}
                                    variant="contained">
                                Elimina
                            </Button>
                            :
                            <Button
                                onClick={handleCreateInitiative}

                                style={{
                                    position: 'fixed',
                                    bottom: 20,
                                    marginTop: '15px',
                                    borderRadius: 35,
                                    backgroundColor: "#92d36e",
                                    padding: "18px 36px",
                                    fontSize: "15px"
                                }}
                                variant="contained">
                                CREA
                            </Button>
                    }
                </Grid>
            </Grid>
        </>
    );
}

export default Initiatives;
