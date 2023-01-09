import React, {useState} from "react";
import {Button, Grid, ListItemSecondaryAction, ListItemText, Typography} from "@mui/material";
import List from "@mui/material/List";
import ListItem from "@mui/material/ListItem";
import ListItemButton from "@mui/material/ListItemButton";
import AccountCircleIcon from '@mui/icons-material/AccountCircle';

const UsersAdmin = () => {


    const [usersList, setUsersList] = useState<any[]>(['utente1', 'utente2', 'utente3', 'utente4', 'utente5', 'utente6', 'utente7', 'utente8', 'utente9', 'utente10', 'utente11', 'utente12'])
    const [selectedUser, setSelectedUser] = useState<any>(null)

    const handleUserClick = (user: any) => {
        setSelectedUser(user)
    }

    const handleOperation = () => {
        console.log('MODIFICO I DATI')
    }
    const userListItemStyle = (user: any) => {
        return {
            color: selectedUser === user ? 'green' : 'white'
        }
    }

    return (
        <Grid container direction="row">
            <Grid item xs={12} display="flex" justifyContent="center" alignItems="center">
                <Typography style={{color: '#feac0d', textAlign: 'center', fontSize: '3rem'}}>Moderazione utenti</Typography>
            </Grid>
            <Grid item xs={12} display="flex" justifyContent="center" alignItems="center">
                <List sx={{
                    width: '90%',
                    position: 'relative',
                    overflow: 'auto',
                    maxHeight: 580
                }}>
                    {usersList.map((value, index) => {
                        return (
                            <ListItem key={index} onClick={()=>handleUserClick(value)}>
                                <AccountCircleIcon fontSize='large' sx={{color: selectedUser === value ? 'green' : 'white'}}/>
                                <ListItemButton sx={{color: selectedUser === value ? 'green' : 'white'}}>
                                    <ListItemText primary={<Typography>  {value}</Typography>} secondary={<Typography> email di {value}</Typography>}/>
                                </ListItemButton>
                                <ListItemSecondaryAction>STATO</ListItemSecondaryAction>
                            </ListItem>
                        );
                    })}
                </List>
            </Grid>

            <Grid item xs={6} display="flex" justifyContent='center' alignItems="right">
                <Button style={{
                    borderRadius: 35,
                    backgroundColor: "red",
                    padding: "10px 20px",
                    fontSize: "18px"
                }}
                        variant="contained"
                        onClick={() => {
                            handleOperation()
                        }}>
                    Cancella
                </Button>
            </Grid>
            <Grid item xs={6} display="flex" justifyContent='center' alignItems="right">
                <Button style={{
                    borderRadius: 35,
                    backgroundColor: "#feac0d",
                    padding: "10px 20px",
                    fontSize: "18px"
                }}
                        variant="contained"
                        onClick={() => {
                            handleOperation()
                        }}>
                    Silenzia
                </Button>
            </Grid>
        </Grid>
    );
}

    export default UsersAdmin;