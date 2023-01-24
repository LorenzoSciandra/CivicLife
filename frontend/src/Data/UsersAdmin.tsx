import React, {useEffect, useState} from "react";
import {
    AppBar,
    Button,
    Grid,
    IconButton,
    ListItemSecondaryAction,
    ListItemText,
    Tooltip,
    Typography
} from "@mui/material";
import List from "@mui/material/List";
import ListItem from "@mui/material/ListItem";
import ListItemButton from "@mui/material/ListItemButton";
import AccountCircleIcon from '@mui/icons-material/AccountCircle';
import {getAllUsers, updateUserStatus, User, UserStatus} from "../APIs/UsersAPI";
import {useLocation, useNavigate} from "react-router-dom";
import AdminPanelSettingsIcon from '@mui/icons-material/AdminPanelSettings';
import {isInstanceOfAuthError, logoutUser, TokenData} from "../APIs/OauthAPI";
import DeselectIcon from '@mui/icons-material/Deselect';
import ArrowBackIcon from "@mui/icons-material/ArrowBack";
import Box from "@mui/material/Box";
import Toolbar from "@mui/material/Toolbar";

const UsersAdmin = () => {
    const location = useLocation();
    const navigate = useNavigate()
    const tokenData : TokenData= location.state.token;
    const [usersList, setUsersList] = useState<User[]>([])
    const [selectedUser, setSelectedUser] = useState<User | null>(null)
    const [firstLoad, setFirstLoad] = useState(true)

    const getUsers = async () => {
        const response = await getAllUsers(tokenData)
        if (isInstanceOfAuthError(response)) {
            navigate('/error', {state: {error: response}})
        } else {
            setUsersList(response)
        }

    }

    useEffect(() => {
        if (firstLoad) {
            getUsers()
        }
        setFirstLoad(false)
    })

    const handleUserClick = (user: any) => {
        setSelectedUser(user)
    }

    const handleStatus = async (newStatus: UserStatus) => {
        if (selectedUser !== null) {
            const userUpdateResponse = await updateUserStatus(tokenData, newStatus, selectedUser.email)
            if (typeof userUpdateResponse === 'boolean') {
                if (userUpdateResponse) {
                    getUsers()
                } else {
                    console.log('error')
                }
            } else {
                navigate('/error', {state: {error: userUpdateResponse}})
            }
        }
    }

    const statusTyphografy = (status: UserStatus) => {
        if (status === UserStatus.ACTIVE) {
            return <Typography sx={{color: 'green'}}>Attivo</Typography>
        } else if (status === UserStatus.SUSPENDED) {
            return <Typography sx={{color: 'orange'}}>Sospeso</Typography>
        } else if (status === UserStatus.BANNED) {
            return <Typography sx={{color: 'red'}}>Bannato</Typography>
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

    return (
        <Grid container direction="row">
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
                                <ArrowBackIcon onClick={goBack} style={{fontSize: '3rem', color: 'white'}}/>
                            </IconButton>
                            <Typography variant="h6" component="div" sx={{flexGrow: 1}}
                                        style={{
                                            justifyContent: 'center',
                                            color: 'white',
                                            textAlign: 'center',
                                            fontSize: '1.8rem',
                                        }}>Amministrazione utenti
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
                <List sx={{
                    width: '80%',
                    overflow: 'auto',
                    maxHeight: 580,
                    position: 'fixed',
                    top: 150,
                    bottom: 100
                }}>

                    <Tooltip title="deseleziona" placement="right">
                            <span>
                                <IconButton size={'small'} color="primary" disabled={selectedUser === null}
                                    onClick={() => setSelectedUser(null)}><DeselectIcon/></IconButton>
                            </span>
                    </Tooltip>


                    {usersList.map((value, index) => {
                        return (
                            <ListItem key={index} onClick={() => handleUserClick(value)}>
                                {
                                    value.admin ? <AdminPanelSettingsIcon sx={{color: '#feac0d'}}/> :
                                        <AccountCircleIcon sx={{color: selectedUser === value ? 'green' : 'white'}}/>
                                }
                                <ListItemButton sx={{color: selectedUser === value ? 'green' : 'white'}}>
                                    <ListItemText primary={<Typography
                                        sx={{fontSize: '1.2rem', fontWeight: 'bold'}}>{value.name}</Typography>}
                                                  secondary={<Typography
                                                      sx={{fontSize: '0.8rem'}}>{value.email}</Typography>}/>
                                </ListItemButton>
                                <ListItemSecondaryAction>{statusTyphografy(value.status)}</ListItemSecondaryAction>
                            </ListItem>
                        );
                    })}
                </List>
            </Grid>

            <Grid item xs={4} display="flex" justifyContent='center' alignItems="right">
                <Button
                    disabled={selectedUser === null || selectedUser.status === UserStatus.BANNED || selectedUser.admin}
                    style={{
                        position: 'fixed',
                        bottom: 40,
                        borderRadius: 35,
                        backgroundColor: "red",
                        padding: "10px 20px",
                        fontSize: "18px"
                    }}
                    variant="contained"
                    onClick={() => {
                        handleStatus(UserStatus.BANNED)
                    }}>
                    Banna
                </Button>
            </Grid>
            <Grid item xs={4} display="flex" justifyContent='center' alignItems="right">
                <Button
                    disabled={selectedUser === null || selectedUser.status === UserStatus.SUSPENDED || selectedUser.admin}
                    style={{
                        position: 'fixed',
                        bottom: 40,
                        borderRadius: 35,
                        backgroundColor: "#feac0d",
                        padding: "10px 20px",
                        fontSize: "18px"
                    }}
                    variant="contained"
                    onClick={() => {
                        handleStatus(UserStatus.SUSPENDED)
                    }}>
                    Sospendi
                </Button>
            </Grid>
            <Grid item xs={4} display="flex" justifyContent='center' alignItems="right">
                <Button
                    disabled={selectedUser === null || selectedUser.status === UserStatus.ACTIVE || selectedUser.admin}
                    style={{
                        position: 'fixed',
                        bottom: 40,
                        borderRadius: 35,
                        backgroundColor: "green",
                        padding: "10px 20px",
                        fontSize: "18px"
                    }}
                    variant="contained"
                    onClick={() => {
                        handleStatus(UserStatus.ACTIVE)
                    }}>
                    Attiva
                </Button>
            </Grid>
        </Grid>
    );
}

export default UsersAdmin;