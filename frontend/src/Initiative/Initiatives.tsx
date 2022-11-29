import {Button, Divider, Grid, Modal,} from "@mui/material";
import '../App.css'
import React, {useState} from "react";
import Box from '@mui/material/Box';
import ListItem from '@mui/material/ListItem';
import ListItemButton from "@mui/material/ListItemButton";
import UpperButtonMenu from "../Utils/UpperButtonMenu";
import InitiativeDetails from "./initiativeDetails";

const Initiatives = () => {

    const buttons = ['Tutte', 'Mie', 'Sottoscritte']
    const [allInitiativesList, setAllInitiativesList] = useState<any[]>(['Iniziativa1', 'Iniziativa2', 'Iniziativa3', 'Iniziativa4', 'Iniziativa5', 'Iniziativa6', 'Iniziativa7'])
    const [myInitiativesList, setMyInitiativesList] = useState<any[]>(['Mia Iniziativa1', 'Mia Iniziativa2', 'Mia Iniziativa3', 'Mia Iniziativa4'])
    const [subcribedInitiativesList, setSubscribedInitiativesList] = useState<any[]>(['sottoscritta1', 'sottoscritta2'])
    const [showingList, setShowingList] = useState<any[]>(allInitiativesList)
    const [showModal, setShowModal] = useState(false)
    const [activeButton, setActiveButton] = useState(buttons[0]);
    const [clickedInitiative, setClickedInitiative]= useState(null)

    const handleInitiativeDetailsOpen = (value: any) => {
        setClickedInitiative(value)
        setShowModal(true)
    }


    return (
        <>
            <Grid container className="App-header">
                <Grid container direction="row" spacing={2}>
                    <UpperButtonMenu first_label={buttons[0]} second_label={buttons[1]} third_label={buttons[2]}
                                     first_list={allInitiativesList} second_list={myInitiativesList}
                                     third_list={subcribedInitiativesList} listSetter={setShowingList}
                                     buttonSetter={setActiveButton}/>
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
                            <Grid item xs={12} display="flex" justifyContent='center' alignItems="right">
                                <Button style={{
                                    marginTop: '15px',
                                    borderRadius: 35,
                                    backgroundColor: "#92d36e",
                                    padding: "18px 36px",
                                    fontSize: "15px"
                                }}
                                        variant="contained">
                                    CREA
                                </Button>
                            </Grid>
                        </Box>
                    </Grid>
                </Grid>
            </Grid>
            <Modal open={showModal}>
                <InitiativeDetails initiative={clickedInitiative} handleClose={()=>setShowModal(false)}/>
            </Modal>
        </>

    );
}

export default Initiatives;
