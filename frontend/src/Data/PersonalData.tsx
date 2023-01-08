import {Button, Grid,} from "@mui/material";
import '../App.css'
import React, {useState} from "react";
import ListItem from '@mui/material/ListItem';
import List from '@mui/material/List';
import UpperButtonMenu from "../Utils/UpperButtonMenu";
import {CssTextField, ReadOnlyTextField} from "../Utils/CustomTextFields";
import ListItemButton from "@mui/material/ListItemButton";


const PersonalData = () => {

    const buttons = ['Dati', 'Vaccini', 'Bonus']
    const [dataList, setDataList] = useState<any[]>(['dato1', 'dato2', 'dato3', 'dato4', 'dato5', 'dato6', 'dato7', 'dato8', 'dato9', 'dato10'])
    const [vaxinesList, setvaxinesList] = useState<any[]>(['vaccino1', 'vaccino2', 'vaccino3', 'vaccino4'])
    const [bonusList, setBonusList] = useState<any[]>(['bonus1', 'bonus2'])
    const [showingList, setShowingList] = useState<any[]>(dataList)
    const [activeButton, setActiveButton] = useState(buttons[0]);

    const handleOperation = () => {
        if (activeButton === buttons[0]) {
            console.log('MODIFICO I DATI')
        } else if (activeButton === buttons[1]) {
            console.log('PRENDO I VACCINI')
        } else if (activeButton === buttons[2]) {
            console.log('PRENDO I BONUS')
        }
    }

    return (
            <Grid container direction="row" spacing={2}>
                <UpperButtonMenu first_label={buttons[0]} second_label={buttons[1]} third_label={buttons[2]}
                                 first_list={dataList} second_list={vaxinesList} third_list={bonusList}
                                 listSetter={setShowingList} buttonSetter={setActiveButton}/>
                <Grid item xs={12} display="flex" justifyContent="center" alignItems="center">
                    <List sx={{
                        width: '100%',
                        position: 'relative',
                        overflow: 'auto',
                        maxHeight: 580
                    }}>
                        {showingList.map((value, index) => {
                            return (
                                <ListItem key={index}>
                                    {
                                        activeButton === buttons[0] ?
                                            <CssTextField sx={{input: {color: 'white'}, style: {color: 'white'}}}
                                                          label={value}/>
                                            :
                                            // <ReadOnlyTextField
                                            //     sx={{input: {color: 'white'}, style: {color: 'white'}}}
                                            //     label={value}
                                            //     defaultValue={value}
                                            //     InputProps={{
                                            //         readOnly: true,
                                            //     }}
                                            // />
                                            <ListItemButton>{value}</ListItemButton>
                                    }
                                </ListItem>
                            );
                        })}


                    </List>

                </Grid>
                <Grid item xs={12} display="flex" justifyContent='center' alignItems="right">
                    <Button style={{
                        borderRadius: 35,
                        backgroundColor: "#92d36e",
                        padding: "10px 20px",
                        fontSize: "18px"
                    }}
                            variant="contained"
                            onClick={() => {
                                handleOperation()
                            }}>
                        {
                            activeButton === buttons[0] ? 'Salva' : activeButton === buttons[1] ? 'Aggiungi Vaccino' : 'aggiungi Bonus'
                        }
                    </Button>
                </Grid>
            </Grid>
    );
}

export default PersonalData;
