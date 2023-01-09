import {Button, Divider, Grid, Typography,} from "@mui/material";
import '../App.css'
import React, {useState} from "react";
import ListItem from '@mui/material/ListItem';
import ListItemButton from "@mui/material/ListItemButton";
import UpperButtonMenu from "../Utils/UpperButtonMenu";
import List from "@mui/material/List";
import {useLocation, useNavigate} from "react-router-dom";

const Initiatives = () => {
    const navigate = useNavigate()
    const location = useLocation()
    const buttons = ['Tutte', 'Mie', 'Sottoscritte']
    const [allInitiativesList, setAllInitiativesList] = useState<any[]>(['Iniziativa1', 'Iniziativa2', 'Iniziativa3', 'Iniziativa4', 'Iniziativa5', 'Iniziativa6', 'Iniziativa7', 'Iniziativa8', 'Iniziativa9', 'Iniziativa10', 'Iniziativa11', 'Iniziativa12'])
    const [myInitiativesList, setMyInitiativesList] = useState<any[]>(['Mia Iniziativa1', 'Mia Iniziativa2', 'Mia Iniziativa3', 'Mia Iniziativa4'])
    const [subcribedInitiativesList, setSubscribedInitiativesList] = useState<any[]>(['sottoscritta1', 'sottoscritta2'])
    const [showingList, setShowingList] = useState<any[]>(allInitiativesList)
    const [showModal, setShowModal] = useState(false)
    const [activeButton, setActiveButton] = useState(buttons[0]);
    const [clickedInitiative, setClickedInitiative] = useState(null)
    const isAdmin = location.state?.isAdmin

    const handleInitiativeDetailsOpen = (value: any) => {
        navigate('/initiativeDetails', {
            state: {
                token: location.state?.token,
                email: location.state?.email,
                isAdmin: location.state?.isAdmin,
                initiative: value
            }
        })
    }
    const handleCreateInitiative = () => {
        navigate('/createInitiative', {
            state: {
                token: location.state?.token,
                email: location.state?.email,
                isAdmin: location.state?.isAdmin
            }
        })
    }
    return (
        <>
            <Grid container direction="row" spacing={2}>
                {!isAdmin ? <UpperButtonMenu first_label={buttons[0]} second_label={buttons[1]} third_label={buttons[2]}
                                             first_list={allInitiativesList} second_list={myInitiativesList}
                                             third_list={subcribedInitiativesList} listSetter={setShowingList}
                                             buttonSetter={setActiveButton}/> :
                    <Grid item xs={12} display="flex" justifyContent="center" alignItems="center">
                        <Typography style={{
                            color: '#feac0d', textAlign: 'center', fontSize: '3rem', position: 'fixed',
                            margin: "auto",
                            top: 10,
                        }}>Amministrazione iniziative</Typography>
                    </Grid>}

                <Grid item xs={12} display="flex" justifyContent="center" alignItems="center">
                    <List sx={{
                        width: '80%',
                        overflow: 'auto',
                        maxHeight: 580,
                        position: 'fixed',
                        top: 150,
                        bottom: 100
                    }}>
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
                    </List>
                </Grid>
                <Grid item xs={12} display="flex" justifyContent='center' alignItems="right">
                    {
                        location.state?.isAdmin ?
                            <Button style={{
                                position: 'fixed',
                                bottom: 20,
                                borderRadius: 35,
                                backgroundColor: "red",
                                padding: "18px 36px",
                                fontSize: "15px"
                            }}
                                    variant="contained">
                                Elimina
                            </Button>
                            :

                            <Button
                                onClick={handleCreateInitiative}

                                style={{
                                    position: 'fixed',
                                    bottom: 20,
                                    marginTop: '15px',
                                    borderRadius: 35,
                                    backgroundColor: "#92d36e",
                                    padding: "18px 36px",
                                    fontSize: "15px"
                                }}
                                variant="contained">
                                CREA
                            </Button>
                    }
                </Grid>
            </Grid>
        </>
    );
}

export default Initiatives;
