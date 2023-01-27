import {
    AppBar,
    Button,
    Dialog,
    Divider,
    Grid,
    IconButton,
    ListItemSecondaryAction,
    ListItemText,
    ToggleButton,
    ToggleButtonGroup,
    Typography
} from "@mui/material";
import React, {useEffect, useState} from "react";
import '../App.css'
import ListItem from "@mui/material/ListItem";
import ListItemButton from "@mui/material/ListItemButton";
import List from "@mui/material/List";
import {useLocation, useNavigate} from "react-router-dom";
import DialogTitle from "@mui/material/DialogTitle";
import DialogActions from "@mui/material/DialogActions";
import Box from "@mui/material/Box";
import Toolbar from "@mui/material/Toolbar";
import ArrowBackIcon from "@mui/icons-material/ArrowBack";
import {isInstanceOfAuthError, logoutUser} from "../APIs/OauthAPI";
import {activeButtonColor, ButtonStyleType, inactiveButtonColor} from "../Utils/CustomComponents";
import {
    changeVotationStatus,
    getActiveVotations,
    getEndedVotations,
    getProgrammedVotations,
    Votation,
    VotationStatus
} from "../APIs/VotationsAPI";


const VotationsAdmin = () => {
    const navigate = useNavigate()
    const location = useLocation()
    const tokenData = location.state.token
    const buttons = ['Attive', 'Concluse','Programmate']
    const [clickedVotation, setClickedVotation] = useState<Votation | null>(null)
    const [activeVotations, setactiveVotations] = useState<Votation[] | null>(null)
    const [endedVotations, setEndedVotations] = useState<Votation[] | null>(null)
    const [programmedVotations, setProgrammedVotations] = useState<Votation[] | null>(null)
    const [firstButton, setFirstButton] = useState<ButtonStyleType>(activeButtonColor)
    const [secondButton, setSecondButton] = useState<ButtonStyleType>(inactiveButtonColor)
    const [thirdButton, setThirdButton] = useState<ButtonStyleType>(inactiveButtonColor)
    const [activeButton, setActiveButton] = useState(buttons[0]);
    const [firstLoad, setFirstLoad] = useState(true)
    const [buttonChanged, setButtonChanged] = useState(false)

    const getActives = async () => {
        const response = await getActiveVotations()
        if (isInstanceOfAuthError(response)) {
            navigate('/error', {state: {error: response}})
        } else {
            setactiveVotations(response)
        }
    }

    const getEndeds = async () => {
        const response = await getEndedVotations()
        if (isInstanceOfAuthError(response)) {
            navigate('/error', {state: {error: response}})
        } else {
            setEndedVotations(response)
        }
    }

    const getProgrammed = async () => {
        const response = await getProgrammedVotations(tokenData)
        if (isInstanceOfAuthError(response)) {
            navigate('/error', {state: {error: response}})
        } else {
            setProgrammedVotations(response)
        }
    }

    useEffect(() => {
        if (firstLoad) {
            if (activeVotations === null) {
                getActives()
            }
        }
        setFirstLoad(false)
    }, [])

    useEffect(() => {
        if (buttonChanged) {
            if (activeButton === buttons[0]) {
                getActives()
            }
            if (activeButton === buttons[1]) {
                getEndeds()
            }
            if(activeButton === buttons[2]){
                getProgrammed()
            }
        }
        setButtonChanged(true)
    }, [activeButton])


    const handleVotationDetailsOpen = (value: any) => {
        navigate('/votations/votationDetails', {state: {token: tokenData, votation: value}})
    }

    const handleDisable = async (value: any) => {
        const result = await changeVotationStatus(tokenData, value.title, VotationStatus.TERMINATED)
        if(typeof result === 'boolean'){
            if(result){
                handleDialogClose()
                getActives()
            }
        }else{
            navigate('/error', {state: {error: result}})
        }
    }
    const handleEnable = async (value: any) => {
        const result = await changeVotationStatus(tokenData, value.title, VotationStatus.ACTIVE)
        if(typeof result === 'boolean'){
            if(result){
                handleDialogClose()
                getProgrammed()
            }
        }else{
            navigate('/error', {state: {error: result}})
        }
    }

    const handleVotationClick = (value: any) => {
        setClickedVotation(value)
    }

    const handleDialogClose = () => {
        setClickedVotation(null)
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

    const statusTyphografy = (status: VotationStatus) => {
        if (status === VotationStatus.ACTIVE) {
            return <Typography sx={{color: 'green'}}>Attivo</Typography>
        } else if (status === VotationStatus.TERMINATED) {
            return <Typography sx={{color: 'red'}}>Terminata</Typography>
        } else if( status === VotationStatus.PROGRAMMED){
            return <Typography sx={{color: 'orange'}}>Programmata</Typography>
        }
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
                                Moderazione votazioni
                            </Typography>
                            <Button
                                onClick={logout}
                                style={{
                                    color: 'white',
                                    backgroundColor: "red",
                                }}>

                                logout
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
            <Grid item xs={12} display="flex" justifyContent="center" alignItems="center">
                <List sx={{
                    width: '80%',
                    overflow: 'auto',
                    maxHeight: 580,
                    position: 'fixed',
                    top: 150,
                    bottom: 100
                }}>
                    {activeButton === buttons[0] && activeVotations ? activeVotations.map((value, index) => {
                        return (
                            <>
                                <ListItem key={index} onClick={() => handleVotationClick(value)}>
                                <ListItemButton>
                                    <ListItemText primary={<Typography sx={{fontSize: '1.2rem', fontWeight: 'bold'}}>{value.title}</Typography>}/>
                                </ListItemButton>
                                <ListItemSecondaryAction>{statusTyphografy(value.status)}</ListItemSecondaryAction>
                            </ListItem>
                                <Divider color='white'/>
                            </>
                        );
                    }) : activeButton === buttons[1] && endedVotations ? endedVotations.map((value, index) => {
                        return (
                            <>
                                <ListItem key={index} onClick={() => handleVotationClick(value)}>
                                    <ListItemButton>
                                        <ListItemText primary={<Typography sx={{fontSize: '1.2rem', fontWeight: 'bold'}}>{value.title}</Typography>}/>
                                    </ListItemButton>
                                    <ListItemSecondaryAction>{statusTyphografy(value.status)}</ListItemSecondaryAction>
                                </ListItem>
                                <Divider color='white'/>
                            </>
                        );
                    }) : activeButton === buttons[2] && programmedVotations ? programmedVotations.map((value, index) => {
                        return (
                            <>
                                <ListItem key={index} onClick={() => handleVotationClick(value)}>
                                    <ListItemButton>
                                        <ListItemText primary={<Typography sx={{fontSize: '1.2rem', fontWeight: 'bold'}}>{value.title}</Typography>}/>
                                    </ListItemButton>
                                    <ListItemSecondaryAction>{statusTyphografy(value.status)}</ListItemSecondaryAction>
                                </ListItem>
                                <Divider color='white'/>
                            </>
                        );
                    }):null}
                </List>
            </Grid>
            <Dialog maxWidth={"sm"} fullWidth={true} open={clickedVotation !== null} onClose={handleDialogClose}>
                <DialogTitle>Operazioni su votazione</DialogTitle>

                <DialogActions>
                    <Button onClick={handleDialogClose}>Chiudi</Button>
                    {clickedVotation && clickedVotation.status===VotationStatus.TERMINATED? null:
                        clickedVotation && clickedVotation.status===VotationStatus.ACTIVE?
                            <Button onClick={() => handleDisable(clickedVotation)}>Termina</Button>:
                            clickedVotation && clickedVotation.status===VotationStatus.PROGRAMMED?
                                <Button onClick={() => handleEnable(clickedVotation)}>Attiva</Button>:null}
                    <Button onClick={() => handleVotationDetailsOpen(clickedVotation)}>Visualizza Dettagli</Button>
                </DialogActions>
            </Dialog>
        </Grid>
    )
}

export default VotationsAdmin;