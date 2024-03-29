import {AppBar, Button, Chip, Dialog, Divider, Grid, IconButton, Typography} from "@mui/material";
import React, {useEffect, useState} from "react";
import {CssTextField} from "../Utils/CustomComponents";
import MenuItem from '@mui/material/MenuItem';
import FormControl from '@mui/material/FormControl';
import AccountCircleIcon from '@mui/icons-material/AccountCircle';
import {Dayjs} from "dayjs";
import {DatePicker, LocalizationProvider} from "@mui/x-date-pickers";
import {AdapterDayjs} from "@mui/x-date-pickers/AdapterDayjs";
import DialogTitle from "@mui/material/DialogTitle";
import DialogContent from "@mui/material/DialogContent";
import DialogContentText from "@mui/material/DialogContentText";
import DialogActions from "@mui/material/DialogActions";
import ErrorIcon from '@mui/icons-material/Error';
import CheckCircleIcon from '@mui/icons-material/CheckCircle';
import Box from "@mui/material/Box";
import Toolbar from "@mui/material/Toolbar";
import ArrowBackIcon from "@mui/icons-material/ArrowBack";
import {isInstanceOfAuthError, logoutUser, TokenData} from "../APIs/OauthAPI";
import {useLocation, useNavigate} from "react-router-dom";
import {createInitiative, Initiative, InitiativeType} from "../APIs/InitiativeAPI";
import {getAllUsersEmail} from "../APIs/UsersAPI";

const InitiativeCreateForm = () => {
    const location = useLocation();
    const navigate = useNavigate()
    const types = [InitiativeType.FOOD, InitiativeType.SPORT, InitiativeType.SOCIAL, InitiativeType.OTHER, InitiativeType.EDUCATIONAL, InitiativeType.ENVIRONMENTAL, InitiativeType.HEALTH, InitiativeType.OTHER]
    const [usersList, setUsersList] = useState<string[]| null>(null)
    const [selectedUsers, setSelectedUsers] = useState<string[]>([])
    const [selectedType, setSelectedType] = useState<any>(types[0])
    const [startDate, setStartDate] = useState<Dayjs | null>(null);
    const [endDate, setEndDate] = useState<Dayjs | null>(null);
    const [description, setDescription] = useState<any>(null)
    const [name, setName] = useState<any>(null)
    const [place, setPlace] = useState<any>(null)
    const [open, setOpen] =  useState(false);
    const [errorOpen, setErrorOpen] = useState<any>(null)
    const [errors, setErrors] = useState<any>([])
    const tokenData : TokenData= location.state.token;

    function toTimestamp(strDate: string){
        const datum = Date.parse(strDate);
        return datum/1000;
    }

    const getUsers = async () => {
        const response = await getAllUsersEmail(tokenData)
        if (isInstanceOfAuthError(response)) {
            navigate('/error', {state: {error: response}})
        } else {
            setUsersList(response.filter((email: string) => email !== tokenData.email))
        }
    }

    useEffect(() => {
        if(usersList === null){
            getUsers()
        }
    },[])

    const errorsCheck = () => {
        setErrors([]);
        let errors = []
        if (name && description && place && startDate && endDate && selectedType) {
            if (!startDate.isBefore(endDate)) {
                errors.push('La data di inizio deve essere precedente alla data di fine')
            }
            if (description.length < 150) {
                errors.push('La descrizione deve essere lunga almeno 150 caratteri')
            }
            if(name.length>50){
                errors.push('Il nome deve essere lungo al massimo 50 caratteri')
            }
            if(name.length<5){
                errors.push('Il nome deve essere lungo almeno 5 caratteri')
            }
        } else {
            errors.push('Compila tutti i campi')
        }

        return errors
    }

    const makeid = (length: number) => {
        let result = '';
        let characters = 'ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789';
        let charactersLength = characters.length;
        for ( let i = 0; i < length; i++ ) {
            result += characters.charAt(Math.floor(Math.random() * charactersLength));
        }
        return result;
    }

    const handleCreateInitiative = async () => {
        const errorsChecked = errorsCheck()
        setErrors(errorsChecked)
        if (errorsChecked.length === 0) {

            const newInitiative: Initiative = {
                id: makeid(15),
                name: name,
                description: description,
                type: selectedType,
                idCreator: tokenData.email,
                idOrganizers: selectedUsers,
                idMembers: [],
                startDate: startDate !== null ? toTimestamp(startDate.toString()) : 0,
                endDate: endDate !== null ? toTimestamp(endDate.toString()) : 0,
                location: place,
            }
            const creation_response= await createInitiative(tokenData, newInitiative)
            if( typeof creation_response === 'boolean'){
                if(creation_response===true){
                    setOpen(true)
                } else if(creation_response===false) {
                    setErrorOpen(true)
                } else{
                    navigate('/error', {state: {error: creation_response}})
                }
            }
        }else{
            setErrorOpen(true)
        }
    }

    const handleTypeChange = (event: React.ChangeEvent<HTMLInputElement>) => {
        setSelectedType(event.target.value);
    };
    const handleDescriptionChange = (event: React.ChangeEvent<HTMLInputElement>) => {
        setDescription(event.target.value);
    }
    const handleNameChange = (event: React.ChangeEvent<HTMLInputElement>) => {
        setName(event.target.value);
    }

    const handleLocationChange = (event: React.ChangeEvent<HTMLInputElement>) => {
        setPlace(event.target.value);
    }

    const handleUserAdd = (user: any) => {
        if (!selectedUsers.includes(user)) {
            setSelectedUsers([...selectedUsers, user])
        }
    }

    const handleDelete = (userToDelete: any) => () => {
        setSelectedUsers((users: any[]) => users.filter((user) => user !== userToDelete));
    };

    const handleDialogClose = () => {
        setErrorOpen(false);
    }

    const handleSuccessDialogClose = () => {
        setOpen(false);
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
        <Grid container>
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
            <Grid item xs={12} display="flex" justifyContent="center" alignItems="center">
                <Typography style={{color: '#feac0d', textAlign: 'center', fontSize: '3rem'}}>Crea la tua
                    iniziativa</Typography>
            </Grid>
            <Grid container direction='row' spacing={2}>
                <Grid item xs={12} display="flex" justifyContent="center" alignItems="center">
                    <CssTextField sx={{input: {color: 'white'}, style: {color: 'white'}, width: '70%'}}
                                  label={'Nome'} onChange={handleNameChange}/>
                </Grid>
                <Grid item xs={12} display="flex" justifyContent="center" alignItems="center">
                    <CssTextField sx={{input: {color: 'white'}, style: {color: 'white'}, width: '70%'}}
                                  label={'Descrizione'} onChange={handleDescriptionChange}/>
                </Grid>
                <Grid item xs={12} display="flex" justifyContent="center" alignItems="center">
                    <CssTextField sx={{input: {color: 'white'}, style: {color: 'white'}, width: '70%'}}
                                  label={'Luogo'} onChange={handleLocationChange}/>
                </Grid>
                <Grid item xs={12} display="flex" justifyContent="center" alignItems="center">
                    <CssTextField sx={{width: '70%', input: {color: 'white'}, style: {color: 'white'}}}
                                  select
                                  value={selectedType}
                                  label='Tipo'
                                  onChange={handleTypeChange}
                                  InputProps={{inputProps: {style: {color: 'white',}}}}
                    >
                        {
                            types.map((type) => {
                                return <MenuItem value={type}>{type}</MenuItem>
                            })
                        }
                    </CssTextField>
                </Grid>
                {
                    selectedUsers.length > 0 ?
                        <Grid container direction='row'>
                            {
                                selectedUsers.map((user: any) => {
                                    return (
                                        <Grid item xs={12} display="flex" justifyContent="center" alignItems="center">
                                            <Chip sx={{backgroundColor: '#feac0d', marginTop: '10px'}}
                                                  icon={<AccountCircleIcon sx={{color: 'white'}}/>}
                                                  label={user}
                                                  onDelete={handleDelete(user)}
                                            />
                                        </Grid>)
                                })
                            }
                        </Grid>
                        :
                        null
                }
                <Grid item xs={12} display="flex" justifyContent="center" alignItems="center">
                    <FormControl sx={{width: '70%'}}>
                        <CssTextField sx={{input: {color: 'white'}, style: {color: 'white'}}}
                                      select
                                      label='Organizzatori' InputProps={{inputProps: {style: {color: 'white',}}}}>
                            {
                                usersList ? usersList.map((user) => {
                                    return <MenuItem onClick={() => {handleUserAdd(user)}} value={user}>{user}</MenuItem> }): null
                            }
                        </CssTextField>
                    </FormControl>
                </Grid>
                <Grid item xs={12} display="flex" justifyContent="center" alignItems="center">
                    <LocalizationProvider dateAdapter={AdapterDayjs}>
                        <DatePicker
                            inputFormat="DD/MM/YYYY"
                            label="Data inizio"
                            value={startDate}
                            onChange={(newValue) => {
                                setStartDate(newValue);
                            }}
                            renderInput={(params) => <CssTextField {...params} sx={{
                                width: '70%',
                                input: {color: 'white'},
                                style: {color: 'white'}
                            }}/>}
                        />
                    </LocalizationProvider>
                </Grid>
                <Grid item xs={12} display="flex" justifyContent="center" alignItems="center">
                    <LocalizationProvider dateAdapter={AdapterDayjs}>
                        <DatePicker
                            inputFormat="DD/MM/YYYY"
                            label="Data fine"
                            value={endDate}
                            onChange={(newValue) => {
                                setEndDate(newValue);
                            }}
                            renderInput={(params) => <CssTextField {...params} sx={{
                                width: '70%',
                                input: {color: 'white'},
                                style: {color: 'white'}
                            }}/>}
                        />
                    </LocalizationProvider>
                </Grid>
            </Grid>
            <Grid item xs={12} display="flex" justifyContent="center" alignItems="center">
                <Button
                    onClick={handleCreateInitiative}
                    style={{
                        marginTop: '15px',
                        borderRadius: 35,
                        backgroundColor: "green",
                        padding: "10px 20px",
                        fontSize: "18px"
                    }}
                    variant="contained">
                    CREA
                </Button>
            </Grid>
            <Dialog maxWidth={"sm"} fullWidth={true} open={errorOpen} onClose={handleDialogClose}>
                <DialogTitle>
                        <Chip sx={{color: 'red'}}
                              icon={<ErrorIcon sx={{color: 'red'}}/>}
                              label={'Errore'}
                              variant="outlined"
                        />
                </DialogTitle>
                <DialogContent>
                    <DialogContentText>
                        {errors.length > 0 ?
                            errors.map((error: string) => {
                                return(<><Typography>{error}</Typography><Divider/></>)
                            })
                            :
                            null}
                    </DialogContentText>
                </DialogContent>
                <DialogActions>
                    <Button onClick={handleDialogClose}>Chiudi</Button>
                </DialogActions>
            </Dialog>
            <Dialog maxWidth={"sm"} fullWidth={true} open={open} onClose={handleSuccessDialogClose}>
                <DialogTitle>
                    <Chip sx={{color: 'green'}}
                          icon={<CheckCircleIcon sx={{color: 'red'}}/>}
                          label={'Successo'}
                          variant="outlined"
                    />
                </DialogTitle>
                <DialogActions>
                    <Button onClick={handleSuccessDialogClose}>Continua a creare</Button>
                    <Button onClick={goBack}>Vai alla iniziative</Button>
                </DialogActions>
            </Dialog>
        </Grid>
    )
}

export default InitiativeCreateForm