import {
    CardMedia,
    createTheme,
    Divider,
    Grid,
    IconButton, Modal,
    styled,
    TextField,
    ThemeProvider,
    ToggleButton,
    ToggleButtonGroup,
    Typography,
} from "@mui/material";
import KeyboardBackspaceIcon from "@mui/icons-material/KeyboardBackspace";
import './App.css'
import React, {useState} from "react";
import Box from '@mui/material/Box';
import ListItem from '@mui/material/ListItem';
import logo_CivicLife from "./imgs/logo_CivicLife.png"
import ListItemButton from "@mui/material/ListItemButton";
import {useActionData} from "react-router-dom";
import UpperButtonMenu from "./UpperButtonMenu";

const PersonalData = () => {
    const theme = createTheme({
            typography: {
                fontFamily: [
                    'ubuntu'
                ].join(','),
            },
        }
    );

    const buttons = ['Tutte', 'Mie', 'Sottoscritte']
    const [allInitiativesList, setAllInitiativesList] = useState<any[]>(['Iniziativa1', 'Iniziativa2', 'Iniziativa3', 'Iniziativa4', 'Iniziativa5', 'Iniziativa6', 'Iniziativa7'])
    const [myInitiativesList, setMyInitiativesList] = useState<any[]>(['Mia Iniziativa1', 'Mia Iniziativa2', 'Mia Iniziativa3', 'Mia Iniziativa4'])
    const [subcribedInitiativesList, setSubscribedInitiativesList] = useState<any[]>(['sottoscritta1', 'sottoscritta2'])
    const [showingList, setShowingList]= useState<any[]>(allInitiativesList)
    const [showModal, setShowModal]=useState(false)

    const handleInitiativeDetailsOpen=()=>{
        // do something to show details
        //setShowModal(true)
    }


    return (
        <ThemeProvider theme={theme}>
            <Grid container className="App-header">
                <Grid container direction="row" spacing={2}>
                    <UpperButtonMenu first_label={buttons[0]} second_label={buttons[1]} third_label={buttons[2]} first_list={allInitiativesList} second_list={myInitiativesList} third_list={subcribedInitiativesList} listSetter={setShowingList} buttonSetter={null}/>
                    <Grid item xs={12} display="flex" justifyContent="center" alignItems="center">
                        <Box sx={{width: '70%', height: '100%'}}>
                            {showingList.map((value, index) => {
                                return (
                                    <><
                                        ListItem key={index}>
                                            <ListItemButton onClick={()=>handleInitiativeDetailsOpen()}>{value}</ListItemButton>
                                        </ListItem>
                                        <Divider color='white'/>
                                    </>
                                );
                            })}
                        </Box>
                    </Grid>
                </Grid>
            </Grid>
            <Modal open={showModal} >
                <Typography>dettagli</Typography>
            </Modal>

        </ThemeProvider>
    );
}

export default PersonalData;
