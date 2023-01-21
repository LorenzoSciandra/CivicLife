import {AppBar, Button, Chip, Container, Grid, IconButton, styled, Typography,} from "@mui/material";
import '../App.css'
import React, {useEffect, useState} from "react";
import Box from '@mui/material/Box';
import {CssTextField} from "../Utils/CustomTextFields";
import {useLocation, useNavigate} from "react-router-dom";
import Toolbar from "@mui/material/Toolbar";
import ArrowBackIcon from "@mui/icons-material/ArrowBack";
import {isInstanceOfAuthError, logoutUser} from "../APIs/OauthAPI";
import AccountCircleIcon from "@mui/icons-material/AccountCircle";
import FormControl from "@mui/material/FormControl";
import MenuItem from "@mui/material/MenuItem";
import {getAllUsersEmail, UserStatus} from "../APIs/UsersAPI";
import dayjs, {Dayjs} from "dayjs";
import {
    changeOrganizers,
    deleteInitiative, getInitiativeByID,
    Initiative,
    InitiativeType,
    InitiativeTypeColor,
    modifyInitiative, subscribeInitiative, unsubscribeInitiative
} from "../APIs/InitiativeAPI";
import {AdapterDayjs} from "@mui/x-date-pickers/AdapterDayjs";
import {DatePicker, LocalizationProvider} from "@mui/x-date-pickers";
import MuiAlert, {AlertProps} from "@mui/material/Alert";
import Snackbar from "@mui/material/Snackbar";
import Stack from "@mui/material/Stack";



const InitiativeDetails = () => {
    const location = useLocation()
    const initiativePassed = location.state.initiative
    const tokenData = location.state.token
    const user = location.state.user
    const isVisitor = location.state.isVisitor
    const navigate = useNavigate()

    const [initiative, setInitiative] = useState<Initiative|null>(null)

    const [modifiedDescription, setModifiedDescription] = useState<string|null>(null)
    const [usersList, setUsersList] = useState<string[]| null>(null)
    const [modifiedSelectedUsers, setModifiedSelectedUsers] = useState<string[]|null>(null)
    const [modifiedStartDate, setModifiedStartDate] = useState<Dayjs | null>(null)
    const [modifiedEndDate, setModifiedEndDate] = useState<Dayjs | null>(null)
    const [initiativeType, setInitiativeType] = useState<InitiativeType | null>(null)

    // DESCRIZIONE
    // DATA INIZIO
    // DATA FINE
    // ORGANIZZATORI

    const getUsers = async () => {
        const response = await getAllUsersEmail(tokenData)
        if (isInstanceOfAuthError(response)) {
            navigate('/error', {state: {error: response}})
        } else {
            setUsersList(response)
        }
    }

    const getInitiative = async (): Promise<boolean> => {
        const response = await getInitiativeByID(tokenData, initiativePassed.id)
        if (isInstanceOfAuthError(response)) {
            navigate('/error', {state: {error: response}})
        }
        else {
            console.log(response)
            setModifiedDescription(response.description)
            setModifiedSelectedUsers(response.idOrganizers)
            setModifiedStartDate(dayjs.unix(response.startDate))
            setModifiedEndDate(dayjs.unix(response.endDate))
            setInitiativeType(InitiativeType[response.type])
            setInitiative(response)
            return true
        }
        return false

    }

    useEffect(() => {
        if(!initiative){
            getInitiative()
        }
    },[])

    useEffect(() => {
        if(initiative){
            //set all


            if(usersList === null && !isVisitor && user.email===initiative.idCreator){
                getUsers()
            }
        }

    },[initiative])

    const somethingChanged = async (message?:string) => {
        const response= await getInitiative()
        if (response){
            if (message) {
                setOpen(true)
                setMessage(message)
            }
        } else if(!response){
            setOpenError(true)
            setMessageError("Errore nel caricamento dei dati")
        } else {
            navigate('/error', {state: {error: response}})
        }
    }


    const handleStartDateChanged = (newValue: Dayjs|null) => {
        if (dayjs(newValue, 'DD/MM/YYYY', true).isValid()) {
            console.log('sto settando',newValue)
            setModifiedStartDate(newValue);
        }
        else{
            console.log("Invalid date")
        }
    }

    const handleEndDateChanged = (newValue: Dayjs|null) => {
        if (dayjs(newValue, 'DD/MM/YYYY', true).isValid()) {
            setModifiedEndDate(newValue);
        }
        else{
            console.log("Invalid date")
        }
    }

    const userIsOrganizer = () => {
        if (initiative) {
            return (initiative.idOrganizers.includes(user.email));
        }
        return false;
    }

    const isReadOnly = () => {
        if(initiative){
            if(userIsOrganizer()){
                return false
            }
            else return user.email !== initiative.idCreator;
        }
    }

    const handleChange = (event: React.ChangeEvent<HTMLInputElement>) => {
        setModifiedDescription(event.target.value)
    }

    function toTimestamp(strDate: string){
        const datum = Date.parse(strDate);
        return datum/1000;
    }

    function checkDataChanged(date: Dayjs | null, oldDate: number){
        if(date === null){
            return false
        }
        else{
            return date.unix() !== oldDate
        }
    }

    const handleModify = async () => {
        if(initiative){
            if (!isReadOnly()) {
                if (initiative.description !== modifiedDescription ||
                    checkDataChanged(modifiedStartDate, initiative.startDate) ||
                    checkDataChanged(modifiedEndDate, initiative.endDate)) {

                    const newInitiative: Initiative = {
                        id: initiative.id,
                        idCreator: initiative.idCreator,
                        idOrganizers: initiative.idOrganizers,
                        description: modifiedDescription && (initiative.description !== modifiedDescription) ? modifiedDescription: initiative.description,
                        startDate: modifiedStartDate && checkDataChanged(modifiedStartDate, initiative.startDate) ? toTimestamp(modifiedStartDate.toString()) : initiative.startDate,
                        endDate: modifiedEndDate && checkDataChanged(modifiedEndDate, initiative.endDate) ? toTimestamp(modifiedEndDate.toString()) : initiative.endDate,
                        type: initiative.type,
                        idMembers: initiative.idMembers,
                        location: initiative.location,
                        name: initiative.name,
                    }
                    console.log(newInitiative)
                    const response = await modifyInitiative(tokenData, newInitiative)
                    if ( typeof response === 'boolean') {
                        if(response){
                            somethingChanged("Dati iniziative modificati con successo")
                        }else{
                            setOpenError(true)
                            setMessageError("Non è stato possibile modificare gli organizzatori")
                        }
                    }
                    else {
                        navigate('/error', {state: {error: response}})

                    }
                }
                if(modifiedSelectedUsers && modifiedSelectedUsers.length > 0) {
                    if (initiative.idOrganizers !== modifiedSelectedUsers) {
                        if (user.email === initiative.idCreator || userIsOrganizer()) {
                            const response = await changeOrganizers(tokenData, initiative.id, modifiedSelectedUsers)
                            if(typeof response === 'boolean'){
                                if (response) {
                                    somethingChanged("Organizzatori modificati con successo")
                                }
                                else{
                                    setOpenError(true)
                                    setMessageError("Non è stato possibile modificare gli organizzatori")
                                }
                            }
                            else {
                                navigate('/error', {state: {error: response}})
                            }
                        } else {
                            console.log('non puoi')
                        }
                    }
                }
            }
        }

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

    const handleUserAdd = (user: any) => {
        if(modifiedSelectedUsers){
            if (!modifiedSelectedUsers.includes(user)) {
                setModifiedSelectedUsers([...modifiedSelectedUsers, user])
            }
        }
    }

    const handleDelete = (userToDelete: any) => () => {
        if(modifiedSelectedUsers){
            setModifiedSelectedUsers(modifiedSelectedUsers.filter((user) => user !== userToDelete))
        }
    };

    const handleSubscribe = async () => {

        if(initiative){
            console.log('subscribe')
            const response = await subscribeInitiative(tokenData, initiative.id)
            if(typeof response === 'boolean'){
                if(response){
                    somethingChanged('Iscrizione avvenuta con successo')
                }
                else{
                    setOpenError(true)
                    setMessageError('Non è stato possibile iscriverti')
                }
            }
            else{
                navigate('/error', {state: {error: response}})
            }
        }

    }

    const handleUnSubscribe = async () => {
        if(initiative){
            console.log('unsubscribe')
            const response = await unsubscribeInitiative(tokenData, initiative.id)
            if(typeof response === 'boolean'){
                if(response){
                    somethingChanged('Sei stato rimosso dall\'iniziative')
                }
                else{
                   setOpenError(true)
                     setMessageError("Non è stato possibile cancellare la tua iscrizione")
                }
            }
            else{
                navigate('/error', {state: {error: response}})
            }
        }
    }

    const [open, setOpen] = useState(false);
    const [message, setMessage] = useState<string>('')
    const [openError, setOpenError] = useState(false);
    const [messageError, setMessageError] = useState<string>('')


    const handleClickError = () => {
        setOpenError(true);
    }

    const handleCloseError = (event?: React.SyntheticEvent| Event, reason?: string) => {
        if (reason === 'clickaway') {
          return;
        }
        setOpenError(false);
    }

    const handleClose = (event?: React.SyntheticEvent | Event, reason?: string) => {
        if (reason === 'clickaway') {
            return;
        }
        setOpen(false);
    };

    const Alert = React.forwardRef<HTMLDivElement, AlertProps>(function Alert(
        props,
        ref,
    ) {
        return <MuiAlert elevation={6} ref={ref} variant="filled" {...props} />;
    });

    const handleDeleteInitiative = async () => {
        if(initiative){
            console.log('delete')
            const response= await deleteInitiative(tokenData, initiative.id)
            console.log(response)
            if(typeof response === 'boolean'){
                if(response){
                    navigate(-1)
                }
                else{
                    setOpenError(true)
                    setMessageError("Non è stato possibile eliminare l'iniziativa")
                }
            }
            else{
                navigate('/error', {state: {error: response}})
            }
        }
    }


    return (
        <>
            {
                initiative ?
                    <><Grid item xs={12} justifyContent="center" alignItems="center">
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
                                                    color: 'white',
                                                    textAlign: 'center',
                                                    fontSize: '1.8rem',
                                                }}>Dettagli initiativa
                                    </Typography>
                                    <Button sx={{color: 'white', backgroundColor: 'red'}}
                                            onClick={logout}>
                                        logout
                                    </Button>
                                </Toolbar>
                            </AppBar>
                        </Box>
                    </Grid><Grid container display="flex" justifyContent="flex-start" alignItems="center"
                                 sx={{width: '100%', marginTop: '65px'}} spacing={3}>

                        <Grid item xs={12}>
                            <Typography
                                style={{
                                    justifyContent: 'center',
                                    color: 'white',
                                    textAlign: 'center',
                                    fontSize: '1.8rem',
                                }}>{initiative.name}
                            </Typography>
                        </Grid>
                        <Grid item xs={12}>
                            <Typography sx={{
                                color: initiativeType? InitiativeTypeColor[initiativeType]: null,
                                fontWeight: 'bold',
                                fontSize: '1.2rem'
                            }}>{initiative.type}</Typography>
                        </Grid>

                        <Grid item xs={12}>
                            <Box sx={{width: '100%', height: '100%', border: '2.5px solid #feac0d',}}>
                                <CssTextField
                                    sx={{backgroundColor: 'white', width: '100%', height: '100%',}}
                                    value={modifiedDescription}
                                    maxRows={5}
                                    onChange={handleChange}
                                    multiline
                                    InputProps={{
                                        readOnly: isReadOnly(),
                                    }}/>
                            </Box>
                        </Grid>

                        <Grid item xs={12}>
                            <Box sx={{width: 1}}>
                                <Box display="grid" gridTemplateColumns="repeat(12, 1fr)" gap={2}>
                                    <Box gridColumn="span 6">
                                        <LocalizationProvider dateAdapter={AdapterDayjs}>
                                            <DatePicker
                                                inputFormat="DD/MM/YYYY"
                                                label='Data inizio'
                                                value={modifiedStartDate}
                                                onChange={!isReadOnly() ? (newValue) => {
                                                    handleStartDateChanged(newValue);
                                                } : () => {
                                                    console.log('non puoi');
                                                }}
                                                renderInput={(params) => <CssTextField {...params} sx={{
                                                    input: {color: 'white'},
                                                    style: {color: 'white'}
                                                }}/>}/>
                                        </LocalizationProvider>
                                    </Box>
                                    <Box gridColumn="span 6">
                                        <LocalizationProvider dateAdapter={AdapterDayjs}>
                                            <DatePicker
                                                inputFormat="DD/MM/YYYY"
                                                label="Data fine"
                                                value={modifiedEndDate}
                                                onChange={!isReadOnly() ? (newValue) => {
                                                    handleEndDateChanged(newValue);
                                                } : () => {
                                                    console.log('non puoi');
                                                }}
                                                renderInput={(params) => <CssTextField {...params} sx={{
                                                    input: {color: 'white'},
                                                    style: {color: 'white'}
                                                }}/>}/>
                                        </LocalizationProvider>
                                    </Box>
                                </Box>
                            </Box>
                        </Grid>

                        <Grid item xs={12}>
                            <Typography sx={{textAlign: "center"}}>Creatore: </Typography>
                        </Grid>
                        <Grid item xs={12} display="flex" justifyContent="center" alignItems="center">
                            <Chip sx={{backgroundColor: '#feac0d'}}
                                  icon={<AccountCircleIcon sx={{color: 'white'}}/>}
                                  label={initiative.idCreator}/>
                        </Grid>
                        <Grid item xs={12}>
                            <Typography sx={{textAlign: "center"}}>Organizzatori: </Typography>
                        </Grid>
                        <Grid item xs={12}>
                            {modifiedSelectedUsers? modifiedSelectedUsers.map((organizer: string) => {
                                return (
                                    <Grid item display="flex" xs={12} justifyContent="center" alignItems="center">
                                        <Chip sx={{backgroundColor: '#feac0d', marginTop: '10px', textAlign: 'center'}}
                                              icon={<AccountCircleIcon sx={{color: 'white'}}/>}
                                              label={organizer}
                                              onDelete={organizer === user.email || user.email === initiative.idCreator ? handleDelete(organizer) : undefined}/>
                                    </Grid>);
                            }): null}
                        </Grid>
                        <Grid item xs={12}>
                            <Typography sx={{textAlign: "center"}}>Sottoscrizioni: </Typography>
                        </Grid>
                        <Grid item xs={12}>
                            {initiative.idMembers.map((member: string) => {
                                return (
                                    <Grid item display="flex" xs={12} justifyContent="center" alignItems="center">
                                        <Chip sx={{backgroundColor: '#feac0d', marginTop: '10px', textAlign: 'center'}}
                                              icon={<AccountCircleIcon sx={{color: 'white'}}/>}
                                              label={member}/>
                                    </Grid>);
                            })}
                        </Grid>
                        {user && user.email === initiative.idCreator && !isVisitor ?
                            <Grid item xs={12} display="flex" justifyContent="center" alignItems="center">
                                <FormControl sx={{width: '70%'}}>
                                    <CssTextField sx={{input: {color: 'white'}, style: {color: 'white'}}}
                                                  select
                                                  label='Organizzatori'>
                                        {usersList ? usersList.map((user) => {
                                            return <MenuItem onClick={() => {
                                                handleUserAdd(user);
                                            }} value={user}>{user}</MenuItem>;
                                        }) : null}
                                    </CssTextField>
                                </FormControl>
                            </Grid> : null}
                        <Grid item xs={12} display="flex" justifyContent="center" alignItems="center">
                            {isVisitor ? null : initiative.idCreator===user.email ?
                                <><Button
                                    disabled={user.status === UserStatus.SUSPENDED}
                                    style={{
                                        borderRadius: 35,
                                        backgroundColor: "#92d36e",
                                        padding: "18px 36px",
                                        fontSize: "15px",
                                    }}
                                    onClick={handleModify} variant="contained">
                                    Modifica
                                </Button>
                                    <Button style={{
                                        borderRadius: 35,
                                        backgroundColor: "red",
                                        padding: "18px 36px",
                                        fontSize: "15px",
                                        marginLeft: '30px'
                                    }} disabled={user.status === UserStatus.SUSPENDED }
                                            variant="contained"
                                            onClick={handleDeleteInitiative}
                                    >
                                        Cancella
                                    </Button></>
                                : userIsOrganizer()?
                                    <Button
                                        disabled={user.status === UserStatus.SUSPENDED}
                                        style={{
                                            borderRadius: 35,
                                            backgroundColor: "#92d36e",
                                            padding: "18px 36px",
                                            fontSize: "15px",
                                        }}
                                        onClick={handleModify} variant="contained">
                                        Modifica
                                    </Button>:
                                !initiative.idMembers.includes(user.email) ?
                                    <Button style={{
                                        borderRadius: 35,
                                        backgroundColor: "#92d36e",
                                        padding: "18px 36px",
                                        fontSize: "15px",
                                    }}
                                            onClick={handleSubscribe}
                                            variant="contained">
                                        Partecipa
                                    </Button> :
                                    <Button style={{
                                        borderRadius: 35,
                                        backgroundColor: "#92d36e",
                                        padding: "18px 36px",
                                        fontSize: "15px",
                                    }}
                                            onClick={handleUnSubscribe}
                                            variant="contained">
                                        Disiscriviti
                                    </Button>}
                        </Grid>
                    </Grid></>: null
            }
            <Stack spacing={2} sx={{width: '100%'}}>
                <Snackbar open={open} autoHideDuration={3000} onClose={handleClose}
                          anchorOrigin={{vertical: "bottom", horizontal: 'center'}}>
                    <Alert onClose={handleClose} severity="success" sx={{width: '100%'}}>
                        {message}
                    </Alert>
                </Snackbar>
            </Stack>
            <Stack spacing={2} sx={{width: '100%'}}>
                <Snackbar open={openError} autoHideDuration={3000} onClose={handleCloseError}
                          anchorOrigin={{vertical: "bottom", horizontal: 'center'}}>
                    <Alert onClose={handleCloseError} severity="warning" sx={{width: '100%'}}>
                        {messageError}
                    </Alert>
                </Snackbar>
            </Stack>
        </>

    );
}

export default InitiativeDetails;