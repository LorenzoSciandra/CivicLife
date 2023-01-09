import {Button, Dialog, Divider, Grid, ListItemText,} from "@mui/material";
import '../App.css'
import React, {useState} from "react";
import List from '@mui/material/List';
import UpperButtonMenu from "../Utils/UpperButtonMenu";
import {CssTextField} from "../Utils/CustomTextFields";
import ListItemButton from "@mui/material/ListItemButton";
import DialogTitle from "@mui/material/DialogTitle";
import DialogContent from "@mui/material/DialogContent";
import DialogActions from "@mui/material/DialogActions";
import DialogContentText from "@mui/material/DialogContentText";


const PersonalData = () => {

    const buttons = ['Dati', 'Vaccini', 'Bonus']
    const [dataList, setDataList] = useState<any[]>(['dato1', 'dato2', 'dato3', 'dato4', 'dato5', 'dato6', 'dato7', 'dato8', 'dato9'])
    const [vaxinesList, setvaxinesList] = useState<any[]>(['vaccino1', 'vaccino2', 'vaccino3', 'vaccino4'])
    const [bonusList, setBonusList] = useState<any[]>(['bonus1', 'bonus2'])
    const [showingList, setShowingList] = useState<any[]>(dataList)
    const [activeButton, setActiveButton] = useState(buttons[0]);
    const [clickedVaxine, setClickedVaxine] = useState(null)
    const [clickedBonus, setClickedBonus] = useState(null)

    const handleOperation = () => {
        if (activeButton === buttons[0]) {
            console.log('MODIFICO I DATI')
        } else if (activeButton === buttons[1]) {
            console.log('PRENDO I VACCINI')
        } else if (activeButton === buttons[2]) {
            console.log('PRENDO I BONUS')
        }
    }

    const handleDialogOpen = (data: any) => {
        console.log('APRO IL DIALOG')
        if (activeButton === buttons[1]) {
            setClickedVaxine(data)
        } else if (activeButton === buttons[2]) {
            setClickedBonus(data)
        }
    }

    const handleDialogClose = () => {
        console.log('CHIUDO IL DIALOG')
        setClickedVaxine(null)
        setClickedBonus(null)
    }

    return (
        <Grid container direction="row" spacing={2}>
            <UpperButtonMenu first_label={buttons[0]} second_label={buttons[1]} third_label={buttons[2]}
                             first_list={dataList} second_list={vaxinesList} third_list={bonusList}
                             listSetter={setShowingList} buttonSetter={setActiveButton}/>
            <Grid item xs={12} display="flex" justifyContent="center" alignItems="center">
                <List sx={{
                    width: '80%',
                    overflow: 'auto',
                    maxHeight: 595,
                    position:'fixed',
                    top: 90,
                    bottom: 100
                }}>
                    {showingList.map((value, index) => {
                        return (
                                    activeButton === buttons[0] ?
                                        <CssTextField sx={{input: {color: 'white'}, style: {color: 'white'}, marginTop: '9px'}}
                                                      label={value + ' etichetta'} defaultValue={value + ' valore'}/>
                                        :
                                        // <ReadOnlyTextField
                                        //     sx={{input: {color: 'white'}, style: {color: 'white'}}}
                                        //     label={value}
                                        //     defaultValue={value}
                                        //     InputProps={{
                                        //         readOnly: true,
                                        //     }}
                                        // />
                                        <>
                                            <ListItemButton onClick={() => handleDialogOpen(value)}>
                                                <ListItemText primary={value}/>
                                            </ListItemButton>
                                            <Divider color={'black'}/>
                                        </>

                        );
                    })}
                </List>
            </Grid>
            <Grid item xs={12} display="flex" justifyContent='center' alignItems="right">
                <Button style={{
                    position:'fixed',
                    bottom: 20,
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
            <Dialog maxWidth={"sm"} fullWidth={true} open={clickedVaxine !== null} onClose={handleDialogClose}>
                <DialogTitle>Dettagli vaccino</DialogTitle>
                <DialogContent>
                    <DialogContentText>
                        <ListItemText sx={{input: {color: 'white'}, style: {color: 'white'}}}
                                      primary={'Nome Vaccino'} secondary={'something'}/>
                        <Divider/>
                        <ListItemText sx={{input: {color: 'white'}, style: {color: 'white'}}}
                                      primary={'Tipo Vaccino'} secondary={'something'}/>
                        <Divider/>
                        <ListItemText sx={{input: {color: 'white'}, style: {color: 'white'}}}
                                      primary={'Data Somministrazione'} secondary={'something'}/>
                        <Divider/><ListItemText sx={{input: {color: 'white'}, style: {color: 'white'}}}
                                                primary={'Luogo Somministrazione'} secondary={'something'}/>
                        <Divider/>
                        <ListItemText sx={{input: {color: 'white'}, style: {color: 'white'}}}
                                      primary={'Descrizione Vaccino'} secondary={'something'}/>
                        <Divider/>
                        <ListItemText sx={{input: {color: 'white'}, style: {color: 'white'}}}
                                      primary={'Dettagli vaccino'} secondary={'something'}/>
                        <Divider/>
                        <ListItemText sx={{input: {color: 'white'}, style: {color: 'white'}}}
                                      primary={'Casa produttrice'} secondary={'something'}/>
                        <Divider/>
                        <ListItemText sx={{input: {color: 'white'}, style: {color: 'white'}}}
                                      primary={'Medico'} secondary={'something'}/>
                        <Divider/>
                        <ListItemText sx={{input: {color: 'white'}, style: {color: 'white'}}}
                                      primary={'Infermiere'} secondary={'something'}/>
                    </DialogContentText>
                </DialogContent>
                <DialogActions>
                    <Button onClick={handleDialogClose}>Chiudi</Button>
                </DialogActions>
            </Dialog>
            <Dialog maxWidth={"sm"} fullWidth={true} open={clickedBonus !== null} onClose={handleDialogClose}>
                <DialogTitle>Dettagli bonus</DialogTitle>
                <DialogContent>
                    <DialogContentText>
                        <ListItemText sx={{input: {color: 'white'}, style: {color: 'white'}}}
                                      primary={'Nome Bonus'} secondary={'something'}/>
                        <Divider/>
                        <ListItemText sx={{input: {color: 'white'}, style: {color: 'white'}}}
                                      primary={'Tipo Bonus'} secondary={'something'}/>
                        <Divider/>
                        <ListItemText sx={{input: {color: 'white'}, style: {color: 'white'}}}
                                      primary={'Data scadenza'} secondary={'something'}/>
                        <Divider/>
                        <ListItemText sx={{input: {color: 'white'}, style: {color: 'white'}}}
                                      primary={'Descrizione'} secondary={'something'}/>

                    </DialogContentText>
                </DialogContent>
                <DialogActions>
                    <Button onClick={handleDialogClose}>Chiudi</Button>
                </DialogActions>
            </Dialog>
        </Grid>
    );
}

export default PersonalData;
