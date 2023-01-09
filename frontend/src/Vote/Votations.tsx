import {Divider, Grid} from "@mui/material";
import UpperButtonMenu from "../Utils/UpperButtonMenu";
import React, {useState} from "react";
import '../App.css'
import Box from "@mui/material/Box";
import ListItem from "@mui/material/ListItem";
import ListItemButton from "@mui/material/ListItemButton";
import List from "@mui/material/List";
import {useLocation, useNavigate} from "react-router-dom";


const Votations = () => {
    const navigate= useNavigate()
    const location= useLocation()
    const buttons = ['Attive', 'Concluse']
    const [activeList, setActiveList] = useState<any[]>(['votazione1', 'votazione2', 'votazione3', 'votazione4', 'votazione5', 'votazione6', 'votazione7', "votazione8", "votazione9", "votazione10"])
    const [endedList, setEndedList] = useState<any[]>(['votazione1 conclusa', 'votazione2 conclusa', 'votazione3 conclusa', 'votazione4 conclusa'])
    const [showingList, setShowingList] = useState<any[]>(activeList)
    const [clickedVotation, setClickedVotation]= useState(null)
    const [showModal, setShowModal] = useState(false)
    const isAdmin= location.state?.isAdmin

    const handleVotationDetailsOpen = (value: any) => {
        navigate('/votations/votationDetails', {state: {token: location.state?.token, email: location.state?.email, isAdmin: location.state?.isAdmin, votation:value}})
    }

    return (
        <Grid container direction="row" spacing={2}>
            <UpperButtonMenu first_label={buttons[0]} second_label={buttons[1]} third_label={null}
                             first_list={activeList} second_list={endedList} third_list={null}
                             listSetter={setShowingList} buttonSetter={null}/>

            <Grid item xs={12} display="flex" justifyContent="center" alignItems="center">
                <List sx={{
                    width: '80%',
                    overflow: 'auto',
                    maxHeight: 580,
                    position:'fixed',
                    top: 150,
                    bottom: 100
                }}>
                    {showingList.map((value, index) => {
                            return (
                                <><
                                    ListItem key={index}>
                                    <ListItemButton
                                        onClick={() => handleVotationDetailsOpen(value)}>{value}</ListItemButton>
                                </ListItem>
                                    <Divider color='white'/>
                                </>
                            );
                        })}
                </List>
            </Grid>
        </Grid>
    )
}

export default Votations;