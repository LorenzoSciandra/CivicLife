import {AppBar, Button, Dialog, Divider, Grid, IconButton, ListItemText, Typography,} from "@mui/material";
import '../App.css'
import React, {useEffect, useState} from "react";
import UpperButtonMenu, {Android12Switch, CssTextField} from "../Utils/CustomComponents";
import DialogTitle from "@mui/material/DialogTitle";
import DialogContent from "@mui/material/DialogContent";
import DialogActions from "@mui/material/DialogActions";
import DialogContentText from "@mui/material/DialogContentText";
import {useLocation, useNavigate} from "react-router-dom";
import type {User} from "../APIs/UsersAPI";
import {
    authorizeBonusAccess,
    authorizeVaccineAccess,
    getLoggedUser,
    isInstanceOfUser,
    updateUser
} from "../APIs/UsersAPI";
import AuthRequired from "./AuthRequired";
import dayjs, {Dayjs} from "dayjs";
import {DatePicker, LocalizationProvider} from "@mui/x-date-pickers";
import {AdapterDayjs} from "@mui/x-date-pickers/AdapterDayjs";
import ListItemButton from "@mui/material/ListItemButton";
import List from "@mui/material/List";
import {BonusType, getAllBonuses, getAllVaccines, VaccineType} from "../APIs/ExternalResourcesAPI";
import Stack from '@mui/material/Stack';
import Snackbar from '@mui/material/Snackbar';
import MuiAlert, {AlertProps} from '@mui/material/Alert';
import AddBoxIcon from '@mui/icons-material/AddBox';
import VaccinesIcon from '@mui/icons-material/Vaccines';
import {AuthError, isInstanceOfAuthError, logoutUser, TokenData} from "../APIs/OauthAPI";
import FormGroup from '@mui/material/FormGroup';
import FormControlLabel from '@mui/material/FormControlLabel';
import FormControl from "@mui/material/FormControl";
import FormLabel from "@mui/material/FormLabel";
import ArrowBackIcon from "@mui/icons-material/ArrowBack";
import Box from '@mui/material/Box';
import Toolbar from '@mui/material/Toolbar';


const PersonalData = () => {
    const location = useLocation()
    const navigate = useNavigate()
    const [user, setUser] = useState<User | null>(null)
    const tokenData : TokenData= location.state.token
    const buttons = ['Dati', 'Vaccini', 'Bonus']
    const [dataList, setDataList] = useState([])
    const [vaccinesList, setVaccinesList] = useState<VaccineType[]>([])
    const [bonusList, setBonusList] = useState<BonusType[]>([])
    const [showingList, setShowingList] = useState(dataList)
    const [activeButton, setActiveButton] = useState(buttons[0]);
    const [clickedVaccine, setClickedVaccine] = useState<VaccineType | null>(null)
    const [clickedBonus, setClickedBonus] = useState<BonusType | null>(null)
    const [showBonusAuthorizationRequiredMessage, setShowBonusAuthorizationRequiredMessage] = useState(false)
    const [showVaccineAuthorizationRequiredMessage, setShowVaccineAuthorizationRequiredMessage] = useState(false)

    const [name, setName] = useState<string>('')
    const [surname, setSurname] = useState<string>('')
    const [birthDate, setBirthDate] = useState<Dayjs | null>(null)
    const [residence, setResidence] = useState<string>('')
    const [domicile, setDomicile] = useState<string>('')
    const [telephonNumber, setTelephonNumber] = useState<number>(0)
    const [fiscalCode, setFiscalCode] = useState<string>('')
    const [message, setMessage] = useState<string>('')
    const [bonusAccess, setBonusAccess] = useState<boolean | null>(null)
    const [vaccineAccess, setVaccineAccess] = useState<boolean | null>(null)

    useEffect(() => {
        if (user === null) {
            somethingChanged()
        }
    })

    useEffect(() => {
        if (user !== null) {
            setName(user.name)
            setSurname(user.surname)
            setBirthDate(user.birthDate !== 0 ? dayjs.unix(user.birthDate): null )
            setResidence(user.residence)
            setDomicile(user.domicile)
            setTelephonNumber(user.telephonNumber)
            setFiscalCode(user.fiscalCode)
            setBonusAccess(user.authorizeBonus)
            setVaccineAccess(user.authorizeVaccine)

            if(user.authorizeBonus){
                loadBonuses()
            }
            if(user.authorizeVaccine){
                loadVaccines()
            }
        }
    }, [user])

    const somethingChanged = async (message?: string) => {
        const response = await getLoggedUser(tokenData)
        if (isInstanceOfUser(response)) {
            setUser(response)
            if (message) {
                handleClick()
                setMessage(message)
            }
        } else {
            navigate('/error', {state: {error: response}})
        }
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
        if (dayjs(newValue, 'DD/MM/YYYY', true).isValid()) {
            setBirthDate(newValue);
        }
        else{
            console.log("Invalid date")
        }
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
        if(isInstanceOfAuthError(bonuslist)){
            navigate('/error', {state: {error: bonuslist}})
        }
        else{
            setBonusList(bonuslist)
            setShowBonusAuthorizationRequiredMessage(false)
            //somethingChanged()
        }
    }

    const loadVaccines = async () => {
        const vaccineList = await getAllVaccines(tokenData)
        if(isInstanceOfAuthError(vaccineList)){
            navigate('/error', {state: {error: vaccineList}})
        }
        else{
            setVaccinesList(vaccineList)
            setShowVaccineAuthorizationRequiredMessage(false)
            //somethingChanged()
        }
    }

    const [open, setOpen] = useState(false);

    const handleClick = () => {
        setOpen(true);
    };

    const handleClose = (event?: React.SyntheticEvent | Event, reason?: string) => {
        if (reason === 'clickaway') {
            return;
        }
        setOpen(false);
    };

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

    const updateUserData = async (): Promise<boolean | AuthError> => {
        if (user) {

            const newUser: User = {
                email: user.email,
                name: name,
                surname: surname,
                admin: user.admin,
                fiscalCode: fiscalCode,
                residence: residence,
                birthDate: birthDate && checkDataChanged(birthDate, user.birthDate) ? toTimestamp(birthDate.toString()) : user.birthDate,
                domicile: domicile,
                status: user.status,
                telephonNumber: Number(telephonNumber),
                authorizeBonus: bonusAccess !== null ? bonusAccess : user.authorizeBonus,
                authorizeVaccine: vaccineAccess !== null ? vaccineAccess : user.authorizeVaccine
            }

            return await updateUser(tokenData, newUser)
        }
        return false
    }

    const authBonus = async () => {
        const updateResult = await authorizeBonusAccess(tokenData)
        if(typeof updateResult === 'boolean') {
            if (updateResult) {
                loadBonuses()
                setShowBonusAuthorizationRequiredMessage(false)
                setBonusAccess(true)
            }
        }
        else{
            navigate('/error', {state: {error: updateResult}})
        }
    }

    const authVaccine = async () => {
        const updateResult = await authorizeVaccineAccess(tokenData)
        if(typeof updateResult === 'boolean'){
            if (updateResult) {
                loadVaccines()
                setShowVaccineAuthorizationRequiredMessage(false)
                setVaccineAccess(true)
            }
        }
        else{
            navigate('/error', {state: {error: updateResult}})
        }
    }

    const handleVaccineAuthorizationChanged = (event: React.ChangeEvent<HTMLInputElement>) => {
        setVaccineAccess(event.target.checked)
    }

    const handleBonusAuthorizationChanged = (event: React.ChangeEvent<HTMLInputElement>) => {
        setBonusAccess(event.target.checked)
    }

    useEffect(() => {
        if (user) {
            setShowVaccineAuthorizationRequiredMessage(false)
            setShowBonusAuthorizationRequiredMessage(false)
            if (activeButton === buttons[1]) {
                if (!user.authorizeVaccine) {
                    setShowVaccineAuthorizationRequiredMessage(true)
                }
            } else if (activeButton === buttons[2]) {
                if (!user.authorizeBonus) {
                    setShowBonusAuthorizationRequiredMessage(true)
                }
            }
        }
    }, [activeButton])

    const handleOperation = async () => {
        if (activeButton === buttons[0]) {
            const update = await updateUserData()
            if (update) {
                somethingChanged('Dati aggiornati correttamente')
            }
        }
    }

    const handleDialogOpen = (data: any) => {
        if (activeButton === buttons[1]) {
            setClickedVaccine(data)
        } else if (activeButton === buttons[2]) {
            setClickedBonus(data)
        }
    }

    const handleDialogClose = () => {
        setClickedVaccine(null)
        setClickedBonus(null)
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
            <UpperButtonMenu first_label={buttons[0]} second_label={buttons[1]} third_label={buttons[2]}
                             first_list={dataList} second_list={vaccinesList} third_list={bonusList}
                             listSetter={setShowingList} buttonSetter={setActiveButton}/>
            {
                showBonusAuthorizationRequiredMessage || showVaccineAuthorizationRequiredMessage ?
                    <>
                        <AuthRequired authFor={showVaccineAuthorizationRequiredMessage ? 'Vaccini' : 'Bonus'}
                                      authFunction={showVaccineAuthorizationRequiredMessage ? authVaccine : authBonus}/>
                    </>
                    : null
            }
            {
                activeButton === buttons[2] && bonusList.length === 0 && user && user.authorizeBonus ?
                    <Grid item xs={12} display="flex" justifyContent="center" alignItems="center">
                        <Typography variant="h4" sx={{paddingLeft: '10px', paddingRight:'10px', textAlign: 'center',}}>Non possiedi bonus</Typography>

                    </Grid>: null
            }
            {
                activeButton === buttons[1] && vaccinesList.length === 0 && user && user.authorizeVaccine ?
                    <Grid item xs={12} display="flex" justifyContent="center" alignItems="center">
                        <Typography variant="h4" sx={{paddingLeft: '10px', paddingRight:'10px', textAlign: 'center',}}>Non possiedi vaccini</Typography>

                    </Grid>: null
            }
            <Grid item xs={12} display="flex" justifyContent="center" alignItems="center">
                <List sx={{
                    width: '85%',
                    overflow: 'auto',
                    maxHeight: 550,
                    position: activeButton === buttons[0] ? 'fixed' : (showBonusAuthorizationRequiredMessage || showVaccineAuthorizationRequiredMessage ? null : 'fixed'),
                    top: '20%',
                    bottom: '1%'
                }}>
                    {
                        user ?
                            activeButton === buttons[0] ?
                                <>
                                    <CssTextField
                                        sx={{input: {color: 'white'}, style: {color: 'white'}, marginTop: '9px'}}
                                        label={'Nome'} defaultValue={user.name} onChange={handleNameChanged}/>
                                    <CssTextField
                                        sx={{input: {color: 'white'}, style: {color: 'white'}, marginTop: '9px'}}
                                        label={'Cognome'} defaultValue={user.surname}
                                        onChange={handleSurnameChanged}/>
                                    <CssTextField
                                        sx={{input: {color: 'white'}, style: {color: 'white'}, marginTop: '9px'}}
                                        label={'Email'} defaultValue={user.email} InputProps={{readOnly: true}}/>
                                    <CssTextField
                                        sx={{input: {color: 'white'}, style: {color: 'white'}, marginTop: '9px'}}
                                        label={'Numero di telefono'} defaultValue={user.telephonNumber}
                                        onChange={handleTelephoneNumberChanged}/>
                                    <CssTextField
                                        sx={{input: {color: 'white'}, style: {color: 'white'}, marginTop: '9px'}}
                                        label={'Codice fiscale'} defaultValue={user.fiscalCode}
                                        onChange={handleFiscalCodeChanged}/>
                                    <CssTextField
                                        sx={{input: {color: 'white'}, style: {color: 'white'}, marginTop: '9px'}}
                                        label={'Residenza'} defaultValue={user.residence}
                                        onChange={handleResidenceChanged}/>
                                    <CssTextField
                                        sx={{input: {color: 'white'}, style: {color: 'white'}, marginTop: '9px'}}
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
                                    <CssTextField
                                        sx={{input: {color: 'white'}, style: {color: 'white'}, marginTop: '9px'}}
                                        label={'Stato'} defaultValue={user.status} InputProps={{
                                        readOnly: true,
                                    }}/>
                                    <Grid item xs={12} display="flex" justifyContent="center" alignItems="center"
                                          sx={{marginTop: '10px', fontSize: '3rem'}}>
                                        <FormControl component='fieldset'>
                                            <FormLabel component='legend'>
                                                <Typography
                                                    sx={{color: 'white', justifyContent: 'center', fontWeight: 'bold'}}>Consenti
                                                    a CivicLife di accedere a:</Typography>
                                            </FormLabel>
                                            <FormGroup aria-label='position' row>
                                                <FormControlLabel control={
                                                    <Android12Switch
                                                        checked={vaccineAccess !== null ? vaccineAccess : false}
                                                        onChange={handleVaccineAuthorizationChanged}
                                                        inputProps={{'aria-label': 'controlled'}}
                                                    />}
                                                                  label="Vaccini?"
                                                                  labelPlacement={'top'}/>
                                                <FormControlLabel control={
                                                    <Android12Switch
                                                        checked={bonusAccess !== null ? bonusAccess : false}
                                                        onChange={handleBonusAuthorizationChanged}
                                                        inputProps={{'aria-label': 'controlled'}}
                                                    />}
                                                                  label="Bonus?"
                                                                  labelPlacement={'top'}/>
                                            </FormGroup>
                                        </FormControl>
                                    </Grid>

                                </>
                                :
                                activeButton === buttons[1]  && vaccineAccess ?

                                    vaccinesList.map((value, index) => {
                                        return (
                                            <>
                                                <ListItemButton onClick={() => handleDialogOpen(value)}>
                                                    <VaccinesIcon sx={{color: 'white', marginRight: '10px'}}/>
                                                    <ListItemText primary={value.vaccineName}/>
                                                </ListItemButton>
                                                <Divider color={'black'}/>
                                            </>
                                        );
                                    })
                                    :
                                    activeButton === buttons[2] && bonusAccess ?

                                    bonusList.map((value, index) => {
                                            return (
                                                <>
                                                    <ListItemButton key={value.id} onClick={() => handleDialogOpen(value)}>
                                                        <AddBoxIcon sx={{color: 'white', marginRight: '10px'}}/>
                                                        <ListItemText primary={value.name}/>
                                                    </ListItemButton>
                                                    <Divider color={'black'}/>
                                                </>
                                            );
                                        }
                                    ): null
                            : null
                    }

                </List>

            </Grid>
            {

                activeButton !== buttons[0] ? null
                    :
                    <Grid item xs={12} display="flex" justifyContent='center' alignItems="right">
                        <Button style={{
                            borderRadius: 35,
                            backgroundColor: "#92d36e",
                            padding: "10px 20px",
                            fontSize: "18px",
                            position:'fixed',
                            bottom:30
                        }}
                                variant="contained"
                                onClick={() => {
                                    handleOperation()
                                }}>
                            Salva
                        </Button>
                    </Grid>
            }


            <Dialog maxWidth={"sm"} fullWidth={true} open={clickedVaccine !== null} onClose={handleDialogClose}>
                <DialogTitle>Dettagli vaccino</DialogTitle>
                <DialogContent>
                    <DialogContentText>
                        <ListItemText primaryTypographyProps={{fontWeight: 'bold'}}
                                      secondaryTypographyProps={{fontSize: '15px'}}
                                      primary={'Nome Vaccino:'} secondary={clickedVaccine ? clickedVaccine.vaccineName : null}/>
                        <Divider/>

                        <ListItemText primaryTypographyProps={{fontWeight: 'bold'}}
                                      secondaryTypographyProps={{fontSize: '15px'}}
                                     primary={'Luogo Somministrazione:'}
                                     secondary={clickedVaccine ? clickedVaccine.location : null}/>
                        <Divider/>

                        <ListItemText primaryTypographyProps={{fontWeight: 'bold'}}
                                      secondaryTypographyProps={{fontSize: '15px'}}
                                      primary={'Data somministrazione:'}
                                      secondary={clickedVaccine ? dayjs.unix(clickedVaccine.date).format('D MMMM YYYY').toString() : null}/>
                        <Divider/>
                        <ListItemText primaryTypographyProps={{fontWeight: 'bold'}}
                                      secondaryTypographyProps={{fontSize: '15px'}}
                                      primary={'Descrizione Vaccino:'}
                                      secondary={clickedVaccine ? clickedVaccine.description : null}/>
                        <Divider/>
                        <ListItemText primaryTypographyProps={{fontWeight: 'bold'}}
                                      secondaryTypographyProps={{fontSize: '15px'}}
                                      primary={'Dose n°: '} secondary={clickedVaccine ? clickedVaccine.dose : null}/>
                        <Divider/>
                        <ListItemText primaryTypographyProps={{fontWeight: 'bold'}}
                                      secondaryTypographyProps={{fontSize: '15px'}}
                                      primary={'Casa produttrice:'}
                                      secondary={clickedVaccine ? clickedVaccine.manufacturer : null}/>
                        <Divider/>
                        <ListItemText primaryTypographyProps={{fontWeight: 'bold'}}
                                      secondaryTypographyProps={{fontSize: '15px'}}
                                      primary={'Medico'} secondary={clickedVaccine ? clickedVaccine.doctor : null}/>
                        <Divider/>
                        <ListItemText primaryTypographyProps={{fontWeight: 'bold'}}
                                      secondaryTypographyProps={{fontSize: '15px'}}
                                      primary={'Infermiere'} secondary={clickedVaccine ? clickedVaccine.nurse : null}/>
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
                        <ListItemText primaryTypographyProps={{fontWeight: 'bold'}}
                                      secondaryTypographyProps={{fontSize: '15px'}}
                                      primary={'Nome Bonus'} secondary={clickedBonus ? clickedBonus.name : null}/>
                        <Divider/>
                        <ListItemText primaryTypographyProps={{fontWeight: 'bold'}}
                                      secondaryTypographyProps={{fontSize: '15px'}}
                                      primary={'Tipo Bonus'} secondary={clickedBonus ? clickedBonus.type : null}/>
                        <Divider/>
                        <ListItemText primaryTypographyProps={{fontWeight: 'bold'}}
                                      secondaryTypographyProps={{fontSize: '15px'}}
                                      primary={'Data scadenza'}
                                      secondary={clickedBonus ? dayjs.unix(clickedBonus.end_date).format('D MMMM YYYY').toString() : null}/>
                        <Divider/>
                        <ListItemText primaryTypographyProps={{fontWeight: 'bold'}}
                                      secondaryTypographyProps={{fontSize: '15px'}}
                                      primary={'Descrizione'}
                                      secondary={clickedBonus ? clickedBonus.description : null}/>

                    </DialogContentText>
                </DialogContent>
                <DialogActions>
                    <Button onClick={handleDialogClose}>Chiudi</Button>
                </DialogActions>
            </Dialog>
            <Stack spacing={2} sx={{width: '100%'}}>
                <Snackbar open={open} autoHideDuration={3000} onClose={handleClose}
                          anchorOrigin={{vertical: "bottom", horizontal: 'center'}}>
                    <Alert onClose={handleClose} severity="success" sx={{width: '100%'}}>
                        {message}
                    </Alert>
                </Snackbar>
            </Stack>
        </Grid>
    );
}

export default PersonalData;
