import {Button, Dialog, Divider, Grid, ListItemText,} from "@mui/material";
import '../App.css'
import React, {useEffect, useState} from "react";
import UpperButtonMenu from "../Utils/UpperButtonMenu";
import DialogTitle from "@mui/material/DialogTitle";
import DialogContent from "@mui/material/DialogContent";
import DialogActions from "@mui/material/DialogActions";
import DialogContentText from "@mui/material/DialogContentText";
import {useLocation} from "react-router-dom";
import type {User} from "../APIs/UsersAPI";
import {getLoggedUser, updateUser} from "../APIs/UsersAPI";
import AuthRequired from "./AuthRequired";
import dayjs, {Dayjs} from "dayjs";
import {CssTextField} from "../Utils/CustomTextFields";
import {DatePicker, LocalizationProvider} from "@mui/x-date-pickers";
import {AdapterDayjs} from "@mui/x-date-pickers/AdapterDayjs";
import ListItemButton from "@mui/material/ListItemButton";
import List from "@mui/material/List";
import {BonusType, getAllBonuses} from "../APIs/ExternalResourcesAPI";
import Stack from '@mui/material/Stack';
import Snackbar from '@mui/material/Snackbar';
import MuiAlert, {AlertProps} from '@mui/material/Alert';


const PersonalData = () => {
    const location = useLocation()
    const [user, setUser] = useState<User>(location.state.user)
    const tokenData = location.state.token
    const buttons = ['Dati', 'Vaccini', 'Bonus']
    const [dataList, setDataList] = useState([user.name, user.surname, user.email, user.birthDate, user.residence, user.domicile, user.telephonNumber, user.status])
    const [vaxinesList, setvaxinesList] = useState([])
    const [bonusList, setBonusList] = useState<BonusType[]>([])
    const [showingList, setShowingList] = useState(dataList)
    const [activeButton, setActiveButton] = useState(buttons[0]);
    const [clickedVaxine, setClickedVaxine] = useState(null)
    const [clickedBonus, setClickedBonus] = useState<BonusType | null>(null)
    const [showBonusAuthorizationRequiredMessage, setShowBonusAuthorizationRequiredMessage] = useState(false)
    const [showVaxineAuthorizationRequiredMessage, setShowVaxineAuthorizationRequiredMessage] = useState(false)
    const [name, setName] = useState(user.name)
    const [surname, setSurname] = useState(user.surname)
    const [birthDate, setBirthDate] = useState<Dayjs | null>(user.birthDate === 0 ? null : dayjs.unix(user.birthDate / 1000))
    const email = user.email
    const [residence, setResidence] = useState(user.residence)
    const [domicile, setDomicile] = useState(user.domicile)
    const [telephonNumber, setTelephonNumber] = useState(user.telephonNumber)
    const [fiscalCode, setFiscalCode] = useState(user.fiscalCode)

    const somethingChanged = async () => {
        console.log('QUALCOSA E CAMBIATO')
        const newuser = await getLoggedUser(tokenData)
        setUser(newuser)
        handleClick()
    }

    const Alert = React.forwardRef<HTMLDivElement, AlertProps>(function Alert(
        props,
        ref,
    ) {
        return <MuiAlert elevation={6} ref={ref} variant="filled" {...props} />;
    });

    const handleNameChanged = (event: React.ChangeEvent<HTMLInputElement>) => {
        setName(event.target.value);
    }
    const handleSurnameChanged = (event: React.ChangeEvent<HTMLInputElement>) => {
        setSurname(event.target.value);
    }
    const handleBirthDateChanged = (newValue: Dayjs | null) => {
        setBirthDate(newValue);
    }
    const handleResidenceChanged = (event: React.ChangeEvent<HTMLInputElement>) => {
        setResidence(event.target.value);
    }
    const handleDomicileChanged = (event: React.ChangeEvent<HTMLInputElement>) => {
        setDomicile(event.target.value);
    }
    const handleTelephoneNumberChanged = (event: React.ChangeEvent<HTMLInputElement>) => {
        setTelephonNumber(parseInt(event.target.value));
    }
    const handleFiscalCodeChanged = (event: React.ChangeEvent<HTMLInputElement>) => {
        setFiscalCode(event.target.value);
    }

    const loadBonuses = async () => {
        const bonuslist = await getAllBonuses(tokenData)
        setBonusList(bonuslist)
        setShowBonusAuthorizationRequiredMessage(false)
    }

    const loadVaxines = () => {
        setShowingList(['Vaxine 1', 'Vaxine 2', 'Vaxine 3'])
        setShowBonusAuthorizationRequiredMessage(false)
    }

    const authVaxines = () => {
        const updateUserData_fake = true//updateUser(tokenData, newUser)
        if (updateUserData_fake) {
            loadVaxines()
            setShowVaxineAuthorizationRequiredMessage(false)

        }
    }

    const [open, setOpen] = React.useState(false);

    const handleClick = () => {
        setOpen(true);
    };

    const handleClose = (event?: React.SyntheticEvent | Event, reason?: string) => {
        if (reason === 'clickaway') {
            return;
        }
        setOpen(false);
    };

    const updateUserData = async (externalResoucesAuth?: string): Promise<boolean> => {
        const newUser: User = {
            email: user.email,
            name: name,
            surname: surname,
            admin: user.admin,
            fiscalCode: fiscalCode,
            residence: residence,
            birthDate: Number(birthDate),
            domicile: domicile,
            status: user.status,
            telephonNumber: Number(telephonNumber),
            authorizeBonus: externalResoucesAuth === 'bonus' ? true : user.authorizeBonus,
            authorizeVaxine: externalResoucesAuth === 'vaxine' ? true : user.authorizeVaxine,
        }
        return await updateUser(tokenData, newUser)
    }

    const authBonus = async () => {
        const updateResult = await updateUserData('bonus')
        if (updateResult) {
            loadBonuses()
            setShowBonusAuthorizationRequiredMessage(false)
            somethingChanged()
        }
    }

    useEffect(() => {
        if (activeButton === buttons[0]) {
            setShowBonusAuthorizationRequiredMessage(false)
            setShowVaxineAuthorizationRequiredMessage(false)
        } else if (activeButton === buttons[1]) {
            setShowBonusAuthorizationRequiredMessage(false)
            if (!user.authorizeVaxine) {
                setShowVaxineAuthorizationRequiredMessage(true)
            } else {
                loadVaxines()
            }
        } else if (activeButton === buttons[2]) {
            setShowVaxineAuthorizationRequiredMessage(false)
            if (!user.authorizeBonus) {
                setShowBonusAuthorizationRequiredMessage(true)
            } else {
                loadBonuses()
            }
        }
    }, [activeButton])


    const handleOperation = async () => {
        if (activeButton === buttons[0]) {
            const update = await updateUserData()
            if (update) {
                somethingChanged()
            }

        } else if (activeButton === buttons[1]) { // qui forse si farà una specie di reload, DA VALUTARE
        } else if (activeButton === buttons[2]) {
        }
    }

    const handleDialogOpen = (data: any) => {
        if (activeButton === buttons[1]) {
            setClickedVaxine(data)
        } else if (activeButton === buttons[2]) {
            setClickedBonus(data)
        }
    }

    const handleDialogClose = () => {
        setClickedVaxine(null)
        setClickedBonus(null)
    }

    return (
        <Grid container direction="row" spacing={2}>
            <UpperButtonMenu first_label={buttons[0]} second_label={buttons[1]} third_label={buttons[2]}
                             first_list={dataList} second_list={vaxinesList} third_list={bonusList}
                             listSetter={setShowingList} buttonSetter={setActiveButton}/>
            {
                showBonusAuthorizationRequiredMessage || showVaxineAuthorizationRequiredMessage ?
                    <>
                        <AuthRequired authFor={showVaxineAuthorizationRequiredMessage ? 'Vaccini' : 'Bonus'}
                                      authFunction={authBonus}/>
                    </>
                    : null
            }
            <Grid item xs={12} display="flex" justifyContent="center" alignItems="center">
                <List sx={{
                    width: '80%',
                    overflow: 'auto',
                    maxHeight: 595,
                    position: activeButton === buttons[0] ? 'fixed' : (showBonusAuthorizationRequiredMessage || showVaxineAuthorizationRequiredMessage ? null : 'fixed'),
                    top: 90,
                    bottom: 100
                }}>
                    {
                        activeButton === buttons[0] ?
                            <>
                                <CssTextField sx={{input: {color: 'white'}, style: {color: 'white'}, marginTop: '9px'}}
                                              label={'Nome'} defaultValue={user.name} onChange={handleNameChanged}/>
                                <CssTextField sx={{input: {color: 'white'}, style: {color: 'white'}, marginTop: '9px'}}
                                              label={'Cognome'} defaultValue={user.surname}
                                              onChange={handleSurnameChanged}/>
                                <CssTextField sx={{input: {color: 'white'}, style: {color: 'white'}, marginTop: '9px'}}
                                              label={'Email'} defaultValue={user.email} InputProps={{readOnly: true}}/>
                                <CssTextField sx={{input: {color: 'white'}, style: {color: 'white'}, marginTop: '9px'}}
                                              label={'Numero di telefono'} defaultValue={user.telephonNumber}
                                              onChange={handleTelephoneNumberChanged}/>
                                <CssTextField sx={{input: {color: 'white'}, style: {color: 'white'}, marginTop: '9px'}}
                                              label={'Codice fiscale'} defaultValue={user.fiscalCode}
                                              onChange={handleFiscalCodeChanged}/>
                                <CssTextField sx={{input: {color: 'white'}, style: {color: 'white'}, marginTop: '9px'}}
                                              label={'Residenza'} defaultValue={user.residence}
                                              onChange={handleResidenceChanged}/>
                                <CssTextField sx={{input: {color: 'white'}, style: {color: 'white'}, marginTop: '9px'}}
                                              label={'Domicilio'} defaultValue={user.domicile}
                                              onChange={handleDomicileChanged}/>
                                <LocalizationProvider dateAdapter={AdapterDayjs}>
                                    <DatePicker
                                        inputFormat="DD/MM/YYYY"
                                        label="Data di nascita"
                                        value={birthDate}
                                        renderInput={(params) => <CssTextField {...params} sx={{
                                            input: {color: 'white'},
                                            style: {color: 'white'},
                                            marginTop: '9px'
                                        }}/>}
                                        onChange={(newValue) => {
                                            handleBirthDateChanged(newValue)
                                        }}/>
                                </LocalizationProvider>
                                <CssTextField sx={{input: {color: 'white'}, style: {color: 'white'}, marginTop: '9px'}}
                                              label={'Stato'} defaultValue={user.status} InputProps={{
                                    readOnly: true,
                                }}/>


                            </>
                            :
                            activeButton === buttons[1] ?

                                vaxinesList.map((value, index) => {
                                    return (
                                        <>
                                            <ListItemButton onClick={() => handleDialogOpen(value)}>
                                                <ListItemText primary={value}/>
                                            </ListItemButton>
                                            <Divider color={'black'}/>
                                        </>
                                    );
                                })
                                :
                                bonusList.map((value, index) => {
                                        return (
                                            <>
                                                <ListItemButton onClick={() => handleDialogOpen(value)}>
                                                    <ListItemText primary={value.name}/>
                                                </ListItemButton>
                                                <Divider color={'black'}/>
                                            </>
                                        );
                                    }
                                )
                    }
                </List>

            </Grid>
            {
                (showBonusAuthorizationRequiredMessage || showVaxineAuthorizationRequiredMessage) ?
                    null
                    :
                    <Grid item xs={12} display="flex" justifyContent='center' alignItems="right">
                        <Button style={{
                            position: 'fixed',
                            bottom: 40,
                            borderRadius: 35,
                            backgroundColor: "#92d36e",
                            padding: "10px 20px",
                            fontSize: "18px"
                        }}
                                variant="contained"
                                onClick={() => {
                                    handleOperation()
                                }}>
                            {
                                activeButton === buttons[0] ? 'Salva' : activeButton === buttons[1] ? 'Aggiungi Vaccino' : 'aggiungi Bonus'
                            }
                        </Button>
                    </Grid>
            }

            <Dialog maxWidth={"sm"} fullWidth={true} open={clickedVaxine !== null} onClose={handleDialogClose}>
                <DialogTitle>Dettagli vaccino</DialogTitle>
                <DialogContent>
                    <DialogContentText>
                        <ListItemText sx={{input: {color: 'white'}, style: {color: 'white'}}}
                                      primary={'Nome Vaccino'} secondary={'something'}/>
                        <Divider/>
                        <ListItemText sx={{input: {color: 'white'}, style: {color: 'white'}}}
                                      primary={'Tipo Vaccino'} secondary={'something'}/>
                        <Divider/>
                        <ListItemText sx={{input: {color: 'white'}, style: {color: 'white'}}}
                                      primary={'Data Somministrazione'} secondary={'something'}/>
                        <Divider/><ListItemText sx={{input: {color: 'white'}, style: {color: 'white'}}}
                                                primary={'Luogo Somministrazione'} secondary={'something'}/>
                        <Divider/>
                        <ListItemText sx={{input: {color: 'white'}, style: {color: 'white'}}}
                                      primary={'Descrizione Vaccino'} secondary={'something'}/>
                        <Divider/>
                        <ListItemText sx={{input: {color: 'white'}, style: {color: 'white'}}}
                                      primary={'Dettagli vaccino'} secondary={'something'}/>
                        <Divider/>
                        <ListItemText sx={{input: {color: 'white'}, style: {color: 'white'}}}
                                      primary={'Casa produttrice'} secondary={'something'}/>
                        <Divider/>
                        <ListItemText sx={{input: {color: 'white'}, style: {color: 'white'}}}
                                      primary={'Medico'} secondary={'something'}/>
                        <Divider/>
                        <ListItemText sx={{input: {color: 'white'}, style: {color: 'white'}}}
                                      primary={'Infermiere'} secondary={'something'}/>
                    </DialogContentText>
                </DialogContent>
                <DialogActions>
                    <Button onClick={handleDialogClose}>Chiudi</Button>
                </DialogActions>
            </Dialog>
            <Dialog maxWidth={"sm"} fullWidth={true} open={clickedBonus !== null} onClose={handleDialogClose}>
                <DialogTitle>Dettagli bonus</DialogTitle>
                <DialogContent>
                    <DialogContentText>
                        <ListItemText sx={{input: {color: 'white'}, style: {color: 'white'}}}
                                      primary={'Nome Bonus'} secondary={clickedBonus ? clickedBonus.name : null}/>
                        <Divider/>
                        <ListItemText sx={{input: {color: 'white'}, style: {color: 'white'}}}
                                      primary={'Tipo Bonus'} secondary={clickedBonus ? clickedBonus.type : null}/>
                        <Divider/>
                        <ListItemText sx={{input: {color: 'white'}, style: {color: 'white'}}}
                                      primary={'Data scadenza'}
                                      secondary={clickedBonus ? dayjs.unix(clickedBonus.end_date).format('D MMMM YYYY').toString() : null}/>
                        <Divider/>
                        <ListItemText sx={{input: {color: 'white'}, style: {color: 'white'}}}
                                      primary={'Descrizione'}
                                      secondary={clickedBonus ? clickedBonus.description : null}/>

                    </DialogContentText>
                </DialogContent>
                <DialogActions>
                    <Button onClick={handleDialogClose}>Chiudi</Button>
                </DialogActions>
            </Dialog>
            <Stack spacing={2} sx={{width: '100%'}}>
                <Snackbar open={open} autoHideDuration={3000} onClose={handleClose} anchorOrigin={{ vertical:"top", horizontal:'center' }}>
                    <Alert onClose={handleClose} severity="success" sx={{width: '100%'}} >
                        operazione completata con successo
                    </Alert>
                </Snackbar>
            </Stack>
        </Grid>
    );
}

export default PersonalData;
