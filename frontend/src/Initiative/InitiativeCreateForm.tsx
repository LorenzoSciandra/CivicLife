import {Button, Chip, Dialog, Divider, Grid, Typography} from "@mui/material";
import React, {useState} from "react";
import {CssTextField} from "../Utils/CustomTextFields";
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

const InitiativeCreateForm = () => {
    const types = ['tipo1', 'tipo2', 'tipo3']
    const [usersList, setUsersList] = useState<any[]>(['utente1', 'utente2', 'utente3', 'utente4', 'utente5', 'utente6', 'utente7', 'utente8', 'utente9', 'utente10', 'utente11', 'utente12'])
    const [selectedUsers, setSelectedUsers] = useState<any>([])
    const [selectedType, setSelectedType] = useState<any>(types[0])
    const [startDate, setStartDate] = useState<Dayjs | null>(null);
    const [endDate, setEndDate] = useState<Dayjs | null>(null);
    const [description, setDescription] = useState<any>(null)
    const [name, setName] = useState<any>(null)
    const [location, setLocation] = useState<any>(null)
    const [open, setOpen] =  useState(false);
    const [errors, setErrors] = useState<any>([])

    const errorsCheck = () => {
        setErrors([]);
        let errors = []
        if (name && description && location && startDate && endDate && selectedType) {
            if (!startDate.isBefore(endDate)) {
                console.log('DATA INIZIO DOPO DATA FINE')
                errors.push('La data di inizio deve essere precedente alla data di fine')
            }
            if (description.length < 20) {
                console.log('DESCRIZIONE TROPPO CORTA')
                errors.push('La descrizione deve essere lunga almeno 20 caratteri')
            }
        } else {
            console.log('DATI MANCANTI')
            errors.push('Compila tutti i campi')
        }
        console.log(errors)
        return errors
    }

    const handleCreateInitiative = () => {
        const errorsChecked = errorsCheck()
        setErrors(errorsChecked)
        if (errorsChecked.length === 0) {
            console.log('CREO INIZIATIVA')
            //TODO: chiamata al backend crea iniziativa
        }
        setOpen(true)
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
        setLocation(event.target.value);
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
        setOpen(false);
    }

    const whiteTypography=(phrase:string) =>{
        return( <Typography sx={{color: 'white'}} variant="h6" component="div">
            {phrase}
        </Typography>)
    }

    return (
        <Grid container>
            {/*    <Grid item xs={12} display="flex" sx={{*/}
            {/*    width: '100%',*/}
            {/*    margin: "auto",*/}
            {/*    top: 0,*/}
            {/*    right: 0*/}
            {/*}}>*/}
            {/*    <IconButton><KeyboardBackspaceIcon sx={{fontSize: 60, color: '#ffffff'}}/></IconButton>*/}
            {/*</Grid>*/}
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
                                      label='Organizzatori'
                                      onChange={handleTypeChange}>
                            {
                                usersList.map((user) => {
                                    return <MenuItem onClick={() => {
                                        handleUserAdd(user)
                                    }} value={user}>{user}</MenuItem>
                                })
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
            <Dialog maxWidth={"sm"} fullWidth={true} open={open} onClose={handleDialogClose}>
                <DialogTitle>
                    {
                    errors.length>0 ?
                        <Chip sx={{color: 'red'}}
                              icon={<ErrorIcon sx={{color: 'red'}}/>}
                              label={'Errore'}
                              variant="outlined"
                        />
                        :
                        <Chip sx={{color: 'green'}}
                              icon={<CheckCircleIcon sx={{color: 'green'}}/>}
                              label={'Successo'}
                              variant="outlined"
                        />
                    }
                </DialogTitle>
                <DialogContent>
                    <DialogContentText>
                        {errors.length > 0 ?
                            errors.map((error: string) => {
                                return(<><Typography>{error}</Typography><Divider/></>)
                            })
                            :
                            <Typography>La tua iniziativa è stata creata con successo!</Typography>}
                    </DialogContentText>
                </DialogContent>
                <DialogActions>
                    <Button onClick={handleDialogClose}>Chiudi</Button>
                </DialogActions>
            </Dialog>
        </Grid>
    )
}

export default InitiativeCreateForm