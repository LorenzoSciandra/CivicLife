import {Button, Grid,} from "@mui/material";
import '../App.css'
import React, {useState} from "react";
import Box from '@mui/material/Box';
import ListItem from '@mui/material/ListItem';
import UpperButtonMenu from "../Utils/UpperButtonMenu";
import {CssTextField, ReadOnlyTextField} from "../Utils/CustomTextFields";


const PersonalData = () => {

    const buttons= ['Dati', 'Vaccini', 'Bonus']
    const [dataList, setDataList] = useState<any[]>(['dato1', 'dato2', 'dato3', 'dato4', 'dato5', 'dato6', 'dato7'])
    const [vaxinesList, setvaxinesList] = useState<any[]>(['vaccino1', 'vaccino2', 'vaccino3', 'vaccino4'])
    const [bonusList, setBonusList] = useState<any[]>(['bonus1', 'bonus2'])
    const [showingList, setShowingList]= useState<any[]>(dataList)
    const [activeButton, setActiveButton] = useState(buttons[0]);

    const handleOperation=()=>{
        if(activeButton===buttons[0]){
            console.log('MODIFICO I DATI')
        } else if(activeButton===buttons[1]){
            console.log('PRENDO I VACCINI')
        }else if(activeButton===buttons[2]){
            console.log('PRENDO I BONUS')
        }
    }

    return (

            <Grid container className="App-header">
                <Grid container direction="row" spacing={2} >
                    <UpperButtonMenu first_label={buttons[0]} second_label={buttons[1]} third_label={buttons[2]} first_list={dataList} second_list={vaxinesList} third_list={bonusList} listSetter={setShowingList} buttonSetter={setActiveButton}/>
                    <Grid item xs={12} display="flex" justifyContent="center" alignItems="center">
                        <Box sx={{width: '70%', height: '100%',position: 'fixed',
                                      margin: "auto",
                                      top:200}}>
                            {showingList.map((value, index) => {
                                return (
                                    <ListItem key={index}>
                                        {
                                            activeButton===buttons[0]?  <CssTextField sx={{ input: { color: 'white' } , style:{color:'white'}}} label={value} />
                                                :
                                                <ReadOnlyTextField
                                                    sx={{ input: { color: 'white' } , style:{color:'white'}}}
                                                    label={value}
                                                    defaultValue={value}
                                                    InputProps={{
                                                        readOnly: true,
                                                    }}
                                                />
                                        }

                                    </ListItem>
                                );
                            })}
                            <Grid item xs={12} display="flex" justifyContent='center' alignItems="right">
                            <Button style={{
                                marginTop:'15px',
                                borderRadius: 35,
                                backgroundColor: "#92d36e",
                                padding: "18px 36px",
                                fontSize: "15px"
                            }}
                                    variant="contained"
                                    onClick={()=>{handleOperation()}}>
                                {
                                    activeButton===buttons[0]? 'Salva': activeButton===buttons[1]? 'Aggiungi Vaccino': 'aggiungi Bonus'
                                }
                            </Button>
                        </Grid>
                        </Box>
                    </Grid>

                </Grid>
            </Grid>
    );
}

export default PersonalData;
