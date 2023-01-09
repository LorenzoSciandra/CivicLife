import {Button, Dialog, Divider, Grid, ListItemText, Typography} from "@mui/material";
import UpperButtonMenu from "../Utils/UpperButtonMenu";
import React, {useState} from "react";
import '../App.css'
import ListItem from "@mui/material/ListItem";
import ListItemButton from "@mui/material/ListItemButton";
import List from "@mui/material/List";
import {useLocation, useNavigate} from "react-router-dom";
import DialogTitle from "@mui/material/DialogTitle";
import DialogContent from "@mui/material/DialogContent";
import DialogContentText from "@mui/material/DialogContentText";
import DialogActions from "@mui/material/DialogActions";


const VotationsAdmin = () => {
    const navigate= useNavigate()
    const location= useLocation()
    const buttons = ['Attive', 'Concluse']
    const [clickedVotation, setClickedVotation]= useState(null)
    const [allVotations, setAllVotations] = useState<any[]>(['votazione1', 'votazione2', 'votazione3', 'votazione4', 'votazione5', 'votazione6', 'votazione7', "votazione8", "votazione9", "votazione10"])

    const handleVotationDetailsOpen = (value: any) => {
        navigate('/votations/votationDetails', {state: {token: location.state?.token, email: location.state?.email, isAdmin: true, votation:value}})
    }

    const handleEnableDisable= (value: any) => {
        console.log('Abilita/Disabilita', value)
    }

    const handleVotationClick = (value: any) => {
        setClickedVotation(value)
    }

    const handleDialogClose = () => {
        setClickedVotation(null)
    }

    return (
        <Grid container direction="row" spacing={2}>
            <Grid item xs={12} display="flex" justifyContent="center" alignItems="center">
                <Typography style={{color: '#feac0d', textAlign: 'center', fontSize: '3rem', position: "fixed", top:10}}>Moderazione votazioni</Typography>
            </Grid>
            <Grid item xs={12} display="flex" justifyContent="center" alignItems="center">
                <List sx={{
                    width: '80%',
                    overflow: 'auto',
                    maxHeight: 580,
                    position:'fixed',
                    top: 150,
                    bottom: 100
                }}>
                    {allVotations.map((value, index) => {
                        return (
                            <><
                                ListItem key={index}>
                                <ListItemButton
                                    onClick={() => handleVotationClick(value)}>{value}</ListItemButton>
                            </ListItem>
                                <Divider color='white'/>
                            </>
                        );
                    })}
                </List>
            </Grid>
            <Dialog maxWidth={"sm"} fullWidth={true} open={clickedVotation !== null} onClose={handleDialogClose}>
                <DialogTitle>Operazioni su votazione</DialogTitle>

                <DialogActions>
                    <Button onClick={handleDialogClose}>Chiudi</Button>
                    <Button onClick={()=>handleEnableDisable(clickedVotation)}>Abilita/Disabilita</Button>
                    <Button onClick={()=>handleVotationDetailsOpen(clickedVotation)}>Visualizza Dettagli</Button>
                </DialogActions>
            </Dialog>
        </Grid>
    )
}

export default VotationsAdmin;