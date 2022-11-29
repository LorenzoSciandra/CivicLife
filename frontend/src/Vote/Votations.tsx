import {Divider, Grid} from "@mui/material";
import UpperButtonMenu from "../Utils/UpperButtonMenu";
import React, {useState} from "react";
import '../App.css'
import Box from "@mui/material/Box";
import ListItem from "@mui/material/ListItem";
import ListItemButton from "@mui/material/ListItemButton";


const Votations = () => {
    const buttons = ['Attive', 'Concluse']
    const [activeList, setActiveList] = useState<any[]>(['votazione1', 'votazione2', 'votazione3', 'votazione4', 'votazione5', 'votazione6', 'votazione7'])
    const [endedList, setEndedList] = useState<any[]>(['votazione1 conclusa', 'votazione2 conclusa', 'votazione3 conclusa', 'votazione4 conclusa'])
    const [showingList, setShowingList] = useState<any[]>(activeList)
    const [clickedVotation, setClickedVotation]= useState(null)
    const [showModal, setShowModal] = useState(false)

    const handleInitiativeDetailsOpen = (value: any) => {
        setClickedVotation(value)
        setShowModal(true)
    }

    return (
        <Grid container direction="row" spacing={2}>
            <UpperButtonMenu first_label={buttons[0]} second_label={buttons[1]} third_label={null}
                             first_list={activeList} second_list={endedList} third_list={null}
                             listSetter={setShowingList} buttonSetter={null}/>

            <Grid item xs={12} display="flex" justifyContent="center" alignItems="center">
                <Box sx={{width: '70%', height: '100%'}}>
                    {showingList.map((value, index) => {
                        return (
                            <><
                                ListItem key={index}>
                                <ListItemButton
                                    onClick={() => handleInitiativeDetailsOpen(value)}>{value}</ListItemButton>
                            </ListItem>
                                <Divider color='white'/>
                            </>
                        );
                    })}
                </Box>
            </Grid>
        </Grid>
    )
}

export default Votations;