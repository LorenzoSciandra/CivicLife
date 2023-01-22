import {AppBar, Button, Divider, Grid, IconButton, ListItem, ListItemButton, Typography} from "@mui/material";
import Box from "@mui/material/Box";

import React, {useEffect, useState} from "react";
import '../App.css'
import {useLocation, useNavigate} from "react-router-dom";
import Toolbar from "@mui/material/Toolbar";
import ArrowBackIcon from "@mui/icons-material/ArrowBack";
import {isInstanceOfAuthError, logoutUser} from "../APIs/OauthAPI";
import {CssTextField} from "../Utils/CustomTextFields";
import List from "@mui/material/List";
import CalendarMonthIcon from '@mui/icons-material/CalendarMonth';
import Chip from "@mui/material/Chip";
import dayjs from "dayjs";
import {getParties, Party} from "../APIs/VotationsAPI";
import GroupsIcon from '@mui/icons-material/Groups';
import {Avatar} from "@mui/material";


const VotationDetails = () => {
    const [partyList, setPartyList] = useState<Party[]|null>(null)

    const location = useLocation()
    const tokenData = location.state.token
    const isVisitor = location.state.isVisitor
    const navigate = useNavigate()
    const votation = location.state.votation
    const user = location.state.user

    const getVotationParties = async () => {
        const response= await getParties(votation.title)
        console.log(response)
        if(isInstanceOfAuthError(response)) {
            navigate('/error', {state: {error: response}})
        }else{
            setPartyList(response)
        }
    }

    useEffect(() => {
        console.log(votation)
        if(votation !== undefined && partyList===null) {
            getVotationParties()
        }

    },[])

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
                  sx={{width: '100%', position:'fixed', top:100}} spacing={3}>
                <Grid item xs={12}>
                    <Box sx={{width: '100%', height: '100%', border: '2.5px solid #feac0d',}}>
                        <CssTextField
                            sx={{input:{color:'white'}, width: '100%', height: '100%',}}
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
                                Dal:    <Chip sx={{backgroundColor:'#feac0d'}}icon={<CalendarMonthIcon/>} label={dayjs.unix(votation.startDate).format('DD/MM/YYYY')}/>
                            </Box>
                            <Box gridColumn="span 6" sx={{textAlign: "center"}}>
                                Al:     <Chip sx={{backgroundColor:'#feac0d'}} icon={<CalendarMonthIcon/>} label={dayjs.unix(votation.endDate).format('DD/MM/YYYY')}/>
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
                        }):null}
                    </List>
                </Grid>
            </Grid>

        </>
    );

}

export default VotationDetails;
