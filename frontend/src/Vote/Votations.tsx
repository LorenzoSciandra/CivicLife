import {AppBar, Button, Divider, Grid, IconButton, ToggleButton, ToggleButtonGroup, Typography} from "@mui/material";
import React, {useEffect, useState} from "react";
import '../App.css'
import ListItem from "@mui/material/ListItem";
import ListItemButton from "@mui/material/ListItemButton";
import List from "@mui/material/List";
import {useLocation, useNavigate} from "react-router-dom";
import Box from "@mui/material/Box";
import Toolbar from "@mui/material/Toolbar";
import ArrowBackIcon from "@mui/icons-material/ArrowBack";
import {isInstanceOfAuthError, logoutUser} from "../APIs/OauthAPI";
import {activeButtonColor, ButtonStyleType, inactiveButtonColor} from "../Utils/CustomTextFields";
import {getActiveVotations, getDoneVotations, getEndedVotations} from "../APIs/VotationsAPI";


const Votations = () => {
    const navigate = useNavigate()
    const location = useLocation()
    const tokenData = location.state.token
    const isVisitor = location.state.isVisitor
    console.log('arriva un visitatore? ' + isVisitor)
    const user = location.state.user
    const buttons = ['Attive', 'Concluse', 'Votate']
    const [activeList, setActiveList] = useState<any[] | null>(null)
    const [endedList, setEndedList] = useState<any[] | null>(null)
    const [votedList, setVotedList] = useState<any[] | null>(null)
    const [clickedVotation, setClickedVotation] = useState(null)
    const [showModal, setShowModal] = useState(false)
    const [firstButton, setFirstButton] = useState<ButtonStyleType>(activeButtonColor)
    const [secondButton, setSecondButton] = useState<ButtonStyleType>(inactiveButtonColor)
    const [thirdButton, setThirdButton] = useState<ButtonStyleType>(inactiveButtonColor)
    const [activeButton, setActiveButton] = useState(buttons[0]);
    const [firstLoad, setFirstLoad] = useState(true)
    const [buttonChanged, setButtonChanged] = useState(false)
    const [loginRequired, setLoginRequired] = useState(false)

    const getActives = async () => {
        const response = await getActiveVotations()
        if (isInstanceOfAuthError(response)) {
            navigate('/error', {state: {error: response}})
        } else {
            setActiveList(response)
        }
    }

    const getEndeds = async () => {
        const response = await getEndedVotations()
        if (isInstanceOfAuthError(response)) {
            navigate('/error', {state: {error: response}})
        } else {
            setEndedList(response)
        }
    }

    const getVoted = async () => {
        const response = await getDoneVotations(tokenData)
        if (isInstanceOfAuthError(response)) {
            navigate('/error', {state: {error: response}})
        } else {
            setVotedList(response)
        }
    }

    useEffect(() => {
        if (firstLoad) {
            if (activeList === null) {
                console.log('first load getting active votations')
                getActives()
            }
        }
        setFirstLoad(false)
    }, [])


    useEffect(() => {
        if (buttonChanged) {
            setLoginRequired(false)
            if (activeButton === buttons[0]) {
                console.log('Attive')
                getActives()
            }
            if (activeButton === buttons[1]) {
                console.log('Concluse')
                getEndeds()
            }
            if (activeButton === buttons[2]) {
                console.log('get Votate votations but only if user is logged')
                if (!isVisitor && user !== null && tokenData !== null) {
                    getVoted()
                } else {
                    setLoginRequired(true)
                }
            }
        }
        setButtonChanged(true)
    }, [activeButton])


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

    const login = () => {
        window.location.assign('http://localhost:8080/login')
    }

    const handleVotationDetailsOpen = (value: any) => {
        navigate('/votations/votationDetails', {state: {token: tokenData, user: user, votation:value, isVisitor: isVisitor}})
    }

    return (
        <Grid container direction="row" spacing={2}>
            <Grid item xs={12} justifyContent="center" alignItems="center">
                <Box sx={{flexGrow: 1}}>
                    <AppBar position="fixed" sx={{backgroundColor: '#3d4347'}}>
                        <Toolbar>
                            <IconButton
                                size="small"
                                edge="start"
                                aria-label="menu"
                                sx={{mr: 2}}
                            >
                                <ArrowBackIcon onClick={goBack} sx={{fontSize: '3rem', color: 'white'}}/>
                            </IconButton>

                            <Typography variant="h6" component="div" sx={{flexGrow: 1}}
                                        style={{
                                            justifyContent: 'center',
                                            color: '#feac0d',
                                            textAlign: 'center',
                                            fontSize: '1.8rem',
                                        }}>
                                Votazioni
                            </Typography>
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
            <Grid item xs={12} display="flex" justifyContent="center" alignItems="center"
                  sx={{position: 'fixed', top: '8%', margin: 'auto', width: '100%'}}>
                <ToggleButtonGroup value={activeButton} exclusive onChange={handleChange} aria-label="Platform">
                    <ToggleButton style={{
                        backgroundColor: firstButton.background,
                        color: firstButton.color,
                        borderColor: '#000000',
                        fontSize: 'calc(15px+0.40vw)'
                    }} value={buttons[0]}>{buttons[0]}</ToggleButton>
                    <ToggleButton style={{
                        backgroundColor: secondButton.background,
                        color: secondButton.color,
                        borderColor: '#000000',
                        fontSize: 'calc(15px+0.40vw)'
                    }} value={buttons[1]}>{buttons[1]}</ToggleButton>
                    <ToggleButton style={{
                        backgroundColor: thirdButton.background,
                        color: thirdButton.color,
                        borderColor: '#000000',
                        fontSize: 'calc(15px+0.40vw)'
                    }} value={buttons[2]}>{buttons[2]}</ToggleButton>
                </ToggleButtonGroup>
            </Grid>
            {loginRequired && activeButton === buttons[2] ?
                <Grid container sx={{position:'fixed', top: '40%'}}>
                    <Grid item xs={12} display="flex" justifyContent="center" alignItems="center">
                        <Typography variant="h4" sx={{marginBottom: 2, paddingLeft: '10px', paddingRight:'10px', textAlign: 'center',}}>Per visualizzare le votazioni già effettuate devi effettuare il login</Typography>

                    </Grid>
                </Grid> : null}
            <Grid item xs={12} display="flex" justifyContent="center" alignItems="center">
                <List sx={{
                    width: '80%',
                    overflow: 'auto',
                    maxHeight: 580,
                    position: 'fixed',
                    top: 150,
                    bottom: 100
                }}>

                    {activeList && activeButton===buttons[0] ? activeList.map((value, index) => {
                        return (
                            <><
                                ListItem key={index}>
                                <ListItemButton
                                    onClick={() => handleVotationDetailsOpen(value)}>{value.title}</ListItemButton>
                            </ListItem>
                                <Divider color='white'/>
                            </>
                        );
                    }) : endedList && activeButton===buttons[1] ? endedList.map((value, index) => {
                        return (


                            <><
                                ListItem key={index}>
                                <ListItemButton
                                    onClick={() => handleVotationDetailsOpen(value)}>{value.title}</ListItemButton>
                            </ListItem>
                                <Divider color='white'/>
                            </>
                        );
                    }) : votedList && activeButton===buttons[2] ? votedList.map((value, index) => {
                        return (
                            <><
                                ListItem key={index}>
                                <ListItemButton
                                    onClick={() => handleVotationDetailsOpen(value)}>{value.title}</ListItemButton>
                            </ListItem>
                                <Divider color='white'/>
                            </>
                        );
                    }) : null
                    }
                </List>
            </Grid>
        </Grid>
    )
}

export default Votations;