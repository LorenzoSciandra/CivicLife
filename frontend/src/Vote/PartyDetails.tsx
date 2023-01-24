import {
    AppBar,
    Button,
    Card,
    CardActionArea,
    CardActions,
    CardContent,
    CardMedia,
    Grid,
    IconButton,
    List,
    Typography
} from "@mui/material";
import Box from "@mui/material/Box";


import React, {useEffect, useState} from "react";

import personalData from "../imgs/personaldata.png"
import {useLocation, useNavigate} from "react-router-dom";
import Toolbar from "@mui/material/Toolbar";
import ArrowBackIcon from "@mui/icons-material/ArrowBack";
import {isInstanceOfAuthError, logoutUser} from "../APIs/OauthAPI";
import {CssTextField} from "../Utils/CustomComponents";
import {Candidate, getCandidates, Votation, voteForCandidate, voteForParty} from "../APIs/VotationsAPI";
import GroupsIcon from "@mui/icons-material/Groups";
import {Simulate} from "react-dom/test-utils";
import Stack from "@mui/material/Stack";
import Snackbar from "@mui/material/Snackbar";
import MuiAlert, {AlertProps} from "@mui/material/Alert";

const PartyDetails = () => {

    const [candidateList, setCandidateList] = useState<any[]|null>(null)
    const location = useLocation()
    const navigate = useNavigate()
    const party = location.state.party
    const tokenData = location.state.token
    const isVisitor = location.state.isVisitor
    const votation :Votation= location.state.votation
    const user = location.state.user

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


    const hasAlreadyVoted = () : boolean=> {
        return votation.votationResult.votersIdList.includes(user.email)
    }

    const getCandidatesList = async () => {
        const response= await getCandidates(party.name)
        if(isInstanceOfAuthError(response)) {
            navigate('/error', {state: {error: response}})
        }
        else{
            setCandidateList(response)
        }
    }

    useEffect(() => {
        if(party !== undefined && candidateList===null) {
            getCandidatesList()
        }

    },[])

    const handleCandidateDetailsOpen = (value: any) => {
        navigate('/votations/votationDetails/partyDetails/candidateDetails', {
            state: {
                token: tokenData,
                user: user,
                votation: votation,
                party: party,
                candidate: value,
                hasVoted: hasAlreadyVoted(),
            }
        })
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

    const voteParty = async () => {
        const response= await voteForParty(tokenData, votation.title, party.name)
        if(typeof response === 'boolean') {
            if(response) {
                setOpen(true)
                navigate('/votations/', {state: {token: tokenData, user: user, isVisitor: isVisitor}})
            }
            else{
                setOpenError(true)
            }
        }else{
            navigate('/error', {state: {error: response}})
        }
    }

    const voteCandidate = async (value: Candidate) => {
        const response = await voteForCandidate(tokenData, votation.title, party.name, value.id)
        if(typeof response === 'boolean') {
            if(response) {
                setOpen(true)
                navigate('/votations/', {state: {token: tokenData, user: user, isVisitor: isVisitor}})
            }
            else{
                setOpenError(true)
            }
        }else{
            navigate('/error', {state: {error: response}})
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
            <Grid container direction='row' spacing={5}>
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
                                {party.name}
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
            <Grid container display="flex" justifyContent="flex-start" alignItems="center"
                  sx={{width: '100%', marginTop: '80px'}} spacing={3}>

                <Grid item xs={12} display="flex" justifyContent='center' alignItems="right">
                        {
                            party.logoLink ? <img style={{width:230, height:200}} alt={party.name} src={party.logoLink}/> :
                                <GroupsIcon/>
                        }
                </Grid>
                <Grid item xs={12}>
                    <Box sx={{width: '100%', height: '100%', border: '2.5px solid #feac0d',}}>
                        <CssTextField
                            sx={{input: {color: 'white'}, width: '100%', height: '100%',}}
                            value={party.description}
                            maxRows={5}
                            multiline
                            InputProps={{
                                readOnly: true,
                                inputProps: {
                                    style: {
                                        color: 'white',
                                    }
                                }
                            }}/>
                    </Box>
                </Grid>
                <Grid item xs={12}>
                    {
                        (user && user.admin) ? null : user && !hasAlreadyVoted() ?
                            <Grid item xs={12} display="flex" justifyContent='center' alignItems="right">
                                <Button style={{
                                    borderRadius: 35,
                                    backgroundColor: "#ff3823",
                                    padding: "10px 20px",
                                    fontSize: "18px"
                                }}
                                        onClick={voteParty}
                                        variant="contained">
                                    VOTA Partito
                                </Button>
                            </Grid> : null
                    }
                </Grid>
                <Grid item xs={12} display="flex" justifyContent="center" alignItems="center">
                    <List sx={{
                        width: '100%',
                        overflow: 'auto',
                        maxHeight: 580,
                    }}>
                    <Grid container direction="column" justifyContent="center" alignItems="center">
                        <Box sx={{width: '70%', height: '100%'}}>
                            <Grid
                                container
                                spacing={1}
                                alignContent="center"
                                wrap="wrap"
                                style={{width: '100%'}}
                            >
                                {candidateList ? candidateList.map((value, index) => {
                                    return (
                                        <>
                                            <Card sx={{maxWidth: 345, margin: '20px'}}>
                                                <CardActionArea onClick={() => {
                                                    handleCandidateDetailsOpen(value)
                                                }}>
                                                    <CardMedia
                                                        component="img"
                                                        image={value.imageLink ? value.imageLink: personalData}
                                                        alt={value.name}
                                                    />
                                                    <CardContent>
                                                        <Typography gutterBottom variant="h5" component="div">
                                                            {value.name} {value.surname}
                                                        </Typography>
                                                    </CardContent>
                                                </CardActionArea>
                                                {
                                                    user && user.admin ? null : user && !hasAlreadyVoted()?
                                                        <CardActions style={{alignItems: 'center'}}>
                                                            <Button size="large" onClick={() => {voteCandidate(value)}}
                                                                    style={{color: '#ff3823', width: '100%'}}>Vota
                                                                Candidato</Button>
                                                        </CardActions> : null
                                                }
                                            </Card>

                                        </>
                                    );
                                }): null}
                            </Grid>
                        </Box>
                    </Grid>
                    </List>
                </Grid>
            </Grid>
            <Stack spacing={2} sx={{width: '100%'}}>
                <Snackbar open={open} autoHideDuration={3000} onClose={handleClose}
                          anchorOrigin={{vertical: "bottom", horizontal: 'center'}}>
                    <Alert onClose={handleClose} severity="success" sx={{width: '100%'}}>
                        Voto registrato con successo!
                    </Alert>
                </Snackbar>
            </Stack>
            <Stack spacing={2} sx={{width: '100%'}}>
                <Snackbar open={openError} autoHideDuration={3000} onClose={handleCloseError}
                          anchorOrigin={{vertical: "bottom", horizontal: 'center'}}>
                    <Alert onClose={handleCloseError} severity="warning" sx={{width: '100%'}}>
                        Non è stato possibile registrare il voto! Riprova più tardi.
                    </Alert>
                </Snackbar>
            </Stack>
        </>
    );
}

export default PartyDetails;