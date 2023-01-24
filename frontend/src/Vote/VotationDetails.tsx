import {
    AppBar,
    Avatar,
    Button,
    Dialog,
    Divider,
    Grid,
    IconButton,
    ListItem,
    ListItemButton,
    ListItemText,
    Typography
} from "@mui/material";
import Box from "@mui/material/Box";

import React, {useEffect, useState} from "react";
import '../App.css'
import {useLocation, useNavigate} from "react-router-dom";
import Toolbar from "@mui/material/Toolbar";
import ArrowBackIcon from "@mui/icons-material/ArrowBack";
import {isInstanceOfAuthError, logoutUser, TokenData} from "../APIs/OauthAPI";
import {CssTextField} from "../Utils/CustomComponents";
import List from "@mui/material/List";
import CalendarMonthIcon from '@mui/icons-material/CalendarMonth';
import Chip from "@mui/material/Chip";
import dayjs from "dayjs";
import {CandidateResult, getParties, Party, PartyResult, Votation} from "../APIs/VotationsAPI";
import GroupsIcon from '@mui/icons-material/Groups';
import DialogActions from "@mui/material/DialogActions";
import DialogContent from "@mui/material/DialogContent";
import DialogContentText from "@mui/material/DialogContentText";
import DialogTitle from "@mui/material/DialogTitle";
import {User} from "../APIs/UsersAPI";


const VotationDetails = () => {
    const [partyList, setPartyList] = useState<Party[] | null>(null)
    const [resultsDialogOpen, setResultsDialogOpen] = useState(false)
    const location = useLocation()
    const tokenData : TokenData= location.state.token
    const isVisitor : boolean= location.state.isVisitor
    const navigate = useNavigate()
    const votation : Votation= location.state.votation
    const user : User = location.state.user
    const showDialog = () => {
        setResultsDialogOpen(true)
    }

    const closeDialog = () => {
        setResultsDialogOpen(false)
    }

    const getVotationParties = async () => {
        const response = await getParties(votation.title)
        if (isInstanceOfAuthError(response)) {
            navigate('/error', {state: {error: response}})
        } else {
            setPartyList(response)
        }
    }

    const getCandidateResult = () => {
        let candidateResult: CandidateResult[] = []
        votation.votationResult.partyResults.forEach((partyResult: PartyResult) => {
            partyResult.candidateResults.forEach((candidate: CandidateResult) => {
                candidateResult.push(candidate)
            })
        })
        return candidateResult
    }

    useEffect(() => {
        if (votation !== undefined && partyList === null) {
            getVotationParties()
        }

    }, [])

    const handlePartyDetailsOpen = (value: any) => {
        navigate('/votations/votationDetails/partyDetails', {
            state: {
                token: tokenData,
                votation: votation,
                party: value,
                isVisitor: isVisitor,
                user: user
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

    const goBack = () => {
        navigate(-1)
    }

    const login = () => {
        window.location.assign('http://localhost:8080/login')
    }

    const percentagetoFixed= (value: number) => {
        value= value*100
        return value.toFixed(2).toString() + '%'
    }

    return (
        <>
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
                                {votation.title}
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
                  sx={{width: '100%', position: 'fixed', top: 100}} spacing={3}>
                <Grid item xs={12}>
                    <Box sx={{width: '100%', height: '100%', border: '2.5px solid #feac0d',}}>
                        <CssTextField
                            sx={{input: {color: 'white'}, width: '100%', height: '100%',}}
                            value={votation.description}
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
                    <Box sx={{width: 1}}>
                        <Box display="grid" gridTemplateColumns="repeat(12, 1fr)" gap={2}>
                            <Box gridColumn="span 6" sx={{textAlign: "center"}}>
                                Dal: <Chip sx={{backgroundColor: '#feac0d'}} icon={<CalendarMonthIcon/>}
                                           label={dayjs.unix(votation.startDate).format('DD/MM/YYYY')}/>
                            </Box>
                            <Box gridColumn="span 6" sx={{textAlign: "center"}}>
                                Al: <Chip sx={{backgroundColor: '#feac0d'}} icon={<CalendarMonthIcon/>}
                                          label={dayjs.unix(votation.endDate).format('DD/MM/YYYY')}/>
                            </Box>
                        </Box>
                    </Box>
                </Grid>
                <Grid item xs={12} display="flex" justifyContent="center" alignItems="center">
                    <List sx={{
                        width: '100%',
                        overflow: 'auto',
                        maxHeight: 580,
                    }}>
                        {partyList ? partyList.map((value, index) => {
                            return (
                                <><
                                    ListItem key={index}>
                                    {
                                        value.logoLink ? <Avatar alt={value.name} src={value.logoLink}/> :
                                            <GroupsIcon/>
                                    }
                                    <ListItemButton onClick={() => {
                                        handlePartyDetailsOpen(value)
                                    }}>{value.name}</ListItemButton>
                                </ListItem>
                                    <Divider color='white'/>
                                </>
                            );
                        }) : null}
                    </List>
                </Grid>
                {
                    votation.status === 'TERMINATED' ?
                        <Grid item xs={12} display="flex" justifyContent="center" alignItems="center">
                            <Button style={{
                                borderRadius: 35,
                                backgroundColor: "#ff3823",
                                padding: "10px 20px",
                                fontSize: "18px"
                            }}
                                    onClick={showDialog}
                                    variant="contained">
                                MOSTRA RISULTATI
                            </Button>
                        </Grid> : null
                }
                <Dialog maxWidth={"sm"} fullWidth={true} open={resultsDialogOpen} onClose={closeDialog}>
                    <DialogTitle>Risultati {votation.title}</DialogTitle>
                    <DialogContent>
                        <DialogContentText>
                            <ListItemText primaryTypographyProps={{fontWeight: 'bold'}}
                                          secondaryTypographyProps={{fontSize: '15px'}}
                                          primary={'Numero di elettori totale:'}
                                          secondary={votation.votationResult.numberOfVotes}/>
                            <Divider/>
                            <ListItemText
                                primaryTypographyProps={{fontWeight: 'bold', fontSize: '19px', color: 'black'}}
                                primary={'Per partito'}/>
                            {
                                votation.votationResult.partyResults.map((value: PartyResult) => {
                                    return (
                                        <>
                                            <ListItem>
                                                <ListItemText sx={{
                                                    fontSize: '15px',
                                                    fontWeight: 'bold',
                                                    color: 'black'
                                                }}>{value.partyId}</ListItemText>
                                                <ListItemText>N° voti: {value.votes}</ListItemText>
                                                <ListItemText>Percentuale: {percentagetoFixed(value.percentage)}</ListItemText>
                                                <Divider/>
                                            </ListItem>

                                        </>
                                    )
                                })
                            }
                            <ListItemText
                                primaryTypographyProps={{fontWeight: 'bold', fontSize: '19px', color: 'black'}}
                                primary={'Per candidato'}/>
                            {
                                getCandidateResult().map((value: CandidateResult) => {
                                    return (
                                        <>
                                            <ListItemText sx={{
                                                fontSize: '15px',
                                                fontWeight: 'bold',
                                                color: 'black'
                                            }}>{value.name} {value.surname}</ListItemText>
                                            <ListItemText>N° voti: {value.votes}</ListItemText>
                                            <ListItemText>Percentuale: {percentagetoFixed(value.percentage)}</ListItemText>

                                        </>
                                    )
                                })
                            }

                        </DialogContentText>
                    </DialogContent>
                    <DialogActions>
                        <Button onClick={closeDialog}>Chiudi</Button>
                    </DialogActions>
                </Dialog>


            </Grid>

        </>
    );

}

export default VotationDetails;
