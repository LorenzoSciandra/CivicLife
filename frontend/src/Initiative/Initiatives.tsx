import {
    AppBar,
    Button,
    Card,
    CardContent,
    CardHeader,
    Grid,
    IconButton,
    ToggleButton,
    ToggleButtonGroup,
    Typography
} from "@mui/material";
import '../App.css'
import React, {useEffect, useState} from "react";
import List from "@mui/material/List";
import {useLocation, useNavigate} from "react-router-dom";
import {
    getAllInitiatives,
    getInitiativesForVisitor,
    getMyInitiatives,
    getOrganizedInitiatives,
    getSubscribedInitiatives,
    Initiative,
    InitiativeNameDesc,
    InitiativeTypeColor
} from "../APIs/InitiativeAPI";
import Box from "@mui/material/Box";
import Toolbar from "@mui/material/Toolbar";
import ArrowBackIcon from "@mui/icons-material/ArrowBack";
import {isInstanceOfAuthError, logoutUser, TokenData} from "../APIs/OauthAPI";
import {User, UserStatus} from "../APIs/UsersAPI";
import {activeButtonColor, ButtonStyleType, inactiveButtonColor} from "../Utils/CustomComponents";


const Initiatives = () => {
    const navigate = useNavigate()
    const location = useLocation()
    const tokenData: TokenData = location.state.token
    const isVisitor: boolean = location.state.isVisitor
    const user : User= location.state.user
    const buttons = ['Tutte', 'Mie', 'Sottoscritte', 'Organizzate']
    const [allInitiativesList, setAllInitiativesList] = useState<Initiative[] | null>(null)
    const [myInitiativesList, setMyInitiativesList] = useState<Initiative[] | null>(null)
    const [readOnlyInitiativeList, setReadOnlyInitiativeList] = useState<InitiativeNameDesc[] | null>(null)
    const [subscribedInitiativesList, setSubscribedInitiativesList] = useState<Initiative[] | null>(null)
    const [organizedInitiativesList, setOrganizedInitiativesList] = useState<Initiative[] | null>(null)
    const [activeButton, setActiveButton] = useState(buttons[0]);
    const [clickedInitiative, setClickedInitiative] = useState(null)
    const [firstButton, setFirstButton] = useState<ButtonStyleType>(activeButtonColor)
    const [secondButton, setSecondButton] = useState<ButtonStyleType>(inactiveButtonColor)
    const [thirdButton, setThirdButton] = useState<ButtonStyleType>(inactiveButtonColor)
    const [fourthButton, setFourthButton] = useState<ButtonStyleType>(inactiveButtonColor)
    const [firstLoad, setFirstLoad] = useState(true)
    const [buttonChanged, setButtonChanged] = useState(false)

    const getInitiatives = async () => {
        const response = await getAllInitiatives(tokenData)
        if (isInstanceOfAuthError(response)) {
            navigate('/error', {state: {error: response}})
        } else {
            console.log(response)
            setAllInitiativesList(response)
        }
    }

    const getInitiativesReadOnly = async () => {
        const response = await getInitiativesForVisitor()
        if (isInstanceOfAuthError(response)) {
            navigate('/error', {state: {error: response}})
        } else {
            setReadOnlyInitiativeList(response)
        }
    }

    const getMy = async () => {
        const response = await getMyInitiatives(tokenData)
        if (isInstanceOfAuthError(response)) {
            navigate('/error', {state: {error: response}})
        } else {
            setMyInitiativesList(response)
        }
    }

    const getSubscribed= async () => {
        const response = await getSubscribedInitiatives(tokenData)
        if (isInstanceOfAuthError(response)) {
            navigate('/error', {state: {error: response}})
        } else {
            setSubscribedInitiativesList(response)
        }
    }

    const getOrganized = async () => {
        const response = await getOrganizedInitiatives(tokenData)
        if (isInstanceOfAuthError(response)) {
            navigate('/error', {state: {error: response}})
        } else {
            setOrganizedInitiativesList(response)
        }
    }



    useEffect(() => {
        if (firstLoad) {
            if (allInitiativesList === null) {
                if (isVisitor===true) {
                    getInitiativesReadOnly()
                } else {
                    getInitiatives()

                }
            }
        }
        setFirstLoad(false)
    }, [])

    useEffect(() => {
        if (buttonChanged) {
            if (activeButton === buttons[0]) {
                getInitiatives()
            }
            if (activeButton === buttons[1]) {
                getMy()
            }
            if (activeButton === buttons[2]) {
                getSubscribed()
            }
            if (activeButton === buttons[3]) {
                getOrganized()
            }
        }
        setButtonChanged(true)
    }, [activeButton])

    const handleInitiativeDetailsOpen = (value: any) => {
        navigate('/initiativeDetails', {
            state: {
                token: tokenData,
                isVisitor: isVisitor,
                user: user,
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
            setFourthButton(inactiveButtonColor)
        } else if (newActiveButton === buttons[1]) {
            setFirstButton(inactiveButtonColor)
            setSecondButton(activeButtonColor)
            setThirdButton(inactiveButtonColor)
            setFourthButton(inactiveButtonColor)
        } else if (newActiveButton === buttons[2]) {
            setFirstButton(inactiveButtonColor)
            setSecondButton(inactiveButtonColor)
            setThirdButton(activeButtonColor)
            setFourthButton(inactiveButtonColor)
        } else if (newActiveButton === buttons[3]) {
            setFirstButton(inactiveButtonColor)
            setSecondButton(inactiveButtonColor)
            setThirdButton(inactiveButtonColor)
            setFourthButton(activeButtonColor)
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

    return (
        <>
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
                                    {user && user.admin ? 'Amministrazione iniziative' : 'Iniziative'}
                                </Typography>
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
                {user && !user.admin ?
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
                            <ToggleButton style={{
                                backgroundColor: fourthButton.background,
                                color: fourthButton.color,
                                borderColor: '#000000',
                                fontSize: 'calc(15px+0.40vw)'
                            }} value={buttons[3]}>{buttons[3]}</ToggleButton>
                        </ToggleButtonGroup>
                    </Grid>
                    : null}

                <Grid item xs={12} display="flex" justifyContent="center" alignItems="center">
                    <List sx={{
                        width: '91%',
                        overflow: 'auto',
                        maxHeight: 550,
                        position: 'fixed',
                        top: '20%',
                        bottom: '1%'
                    }}>
                        {
                            isVisitor && readOnlyInitiativeList ?
                                readOnlyInitiativeList.map((value) => {
                                    return (
                                        <Card sx={{maxWidth:'100%', margin:'50px',"&:hover": {
                                                background: "#d7d7d7"
                                            }}}>
                                            <CardHeader sx={{backgroundColor: InitiativeTypeColor[value.type]}}
                                                title={value.name}
                                                subheader={value.location}
                                            />
                                            <CardContent>
                                                <Typography sx={{fontWeight:'bold'}}variant="body2" color="text.secondary">
                                                    {value.type}
                                                </Typography>
                                                <Typography variant="body1" color="text.secondary">
                                                    {value.description}
                                                </Typography>
                                            </CardContent>
                                        </Card>

                                    )
                                })
                                :
                            (activeButton === buttons[0]) && allInitiativesList ?
                                allInitiativesList.map((value) => {
                                    return (
                                        <Card sx={{maxWidth:'100%', margin:'50px',"&:hover": {
                                                background: "#d7d7d7"
                                            }}} onClick={()=>handleInitiativeDetailsOpen(value)}>
                                            <CardHeader sx={{backgroundColor: InitiativeTypeColor[value.type]}}
                                                        title={value.name}
                                                        subheader={value.location}
                                            />
                                            <CardContent>
                                                <Typography sx={{fontWeight:'bold'}}variant="body2" color="text.secondary">
                                                    {value.type}
                                                </Typography>
                                                <Typography variant="body1" color="text.secondary">
                                                    {value.description}
                                                </Typography>
                                            </CardContent>
                                        </Card>
                                    );
                                })
                                :
                                activeButton === buttons[1] && myInitiativesList ?
                                    myInitiativesList.map((value) => {
                                        return (
                                            <Card sx={{maxWidth:'100%', margin:'50px',"&:hover": {
                                                    background: "#d7d7d7"
                                                }}} onClick={()=>handleInitiativeDetailsOpen(value)}>
                                                <CardHeader sx={{backgroundColor: InitiativeTypeColor[value.type]}}
                                                            title={value.name}
                                                            subheader={value.location}
                                                />
                                                <CardContent>
                                                    <Typography sx={{fontWeight:'bold'}}variant="body2" color="text.secondary">
                                                        {value.type}
                                                    </Typography>
                                                    <Typography variant="body1" color="text.secondary">
                                                        {value.description}
                                                    </Typography>
                                                </CardContent>
                                            </Card>
                                        );
                                    })
                                    :
                                    activeButton === buttons[2] && subscribedInitiativesList ?
                                        subscribedInitiativesList.map((value) => {
                                            return (
                                                <Card sx={{maxWidth:'100%', margin:'50px',"&:hover": {
                                                        background: "#d7d7d7"
                                                    }}} onClick={()=>handleInitiativeDetailsOpen(value)}>
                                                    <CardHeader sx={{backgroundColor: InitiativeTypeColor[value.type]}}
                                                                title={value.name}
                                                                subheader={value.location}
                                                    />
                                                    <CardContent>
                                                        <Typography sx={{fontWeight:'bold'}}variant="body2" color="text.secondary">
                                                            {value.type}
                                                        </Typography>
                                                        <Typography variant="body1" color="text.secondary">
                                                            {value.description}
                                                        </Typography>
                                                    </CardContent>
                                                </Card>
                                            );
                                        }) : activeButton === buttons[3] && organizedInitiativesList ?
                                            organizedInitiativesList.map((value) => {
                                                return (
                                                    <Card sx={{maxWidth:'100%', margin:'50px',"&:hover": {
                                                            background: "#d7d7d7"
                                                        }}} onClick={()=>handleInitiativeDetailsOpen(value)}>
                                                        <CardHeader sx={{backgroundColor: InitiativeTypeColor[value.type]}}
                                                                    title={value.name}
                                                                    subheader={value.location}
                                                        />
                                                        <CardContent>
                                                            <Typography sx={{fontWeight:'bold'}}variant="body2" color="text.secondary">
                                                                {value.type}
                                                            </Typography>
                                                            <Typography variant="body1" color="text.secondary">
                                                                {value.description}
                                                            </Typography>
                                                        </CardContent>
                                                    </Card>
                                                );
                                            }) : null
                        }
                    </List>
                </Grid>
                <Grid item xs={12} display="flex" justifyContent='center' alignItems="right">
                    {
                        isVisitor || (user && user.admin) ?
                            null
                            :
                            <Button
                                disabled={user.status===UserStatus.SUSPENDED}
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
