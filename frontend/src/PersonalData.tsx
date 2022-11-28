import {
    Button,
    CardMedia,
    createTheme,
    Grid,
    IconButton, OutlinedInputProps, styled,
    TextField,
    TextFieldProps,
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
import ListItemButton from '@mui/material/ListItemButton';
import ListItemText from '@mui/material/ListItemText';
import {ListChildComponentProps} from 'react-window';
import basquiaPulito from "./imgs/logo_CivicLife.png"
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

    type ButtonStyleType={
        background: string,
        color: string
    }
    const activeButtonColor: ButtonStyleType = {
        background: '#feac0d',
        color: '#ffffff'
    }
    const inactiveButtonColor: ButtonStyleType = {
        background: '#ffffff',
        color: '#feac0d'
    }
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

    const CssTextField = styled(TextField)({

        '& label.MuiInputLabel-root':{
            color:'white',
        },
        '& label.Mui-focused': {
            color: '#feac0d',
        },
        '& .MuiInput-underline:after': {
            borderBottomColor: '#feac0d',
        },
        '& .MuiOutlinedInput-root': {
            '& fieldset': {
                borderColor: 'white',
            },
            '&:hover fieldset': {
                borderColor: 'white',
            },
            '&.Mui-focused fieldset': {
                borderColor:'#feac0d',
            },
        },
        width:'100%'
    });

    return (
        <ThemeProvider theme={theme}>
            <Grid container className="App-header">
                <Grid container direction="row" spacing={2} >
                    <UpperButtonMenu first_label={buttons[0]} second_label={buttons[1]} third_label={buttons[2]} first_list={dataList} second_list={vaxinesList} third_list={bonusList} listSetter={setShowingList} buttonSetter={setActiveButton}/>
                    <Grid item xs={12} display="flex" justifyContent="center" alignItems="center">
                        <Box sx={{width: '70%', height: '100%'}}>
                            {showingList.map((value, index) => {
                                return (
                                    <ListItem key={index}>
                                        <CssTextField sx={{ input: { color: 'white' } , style:{color:'white'}}} label={value} />
                                    </ListItem>
                                );
                            })}
                        </Box>
                    </Grid>
                    <Grid item xs={12} display="flex" justifyContent='center' alignItems="right">
                        <Button style={{
                            marginLeft: '15px',
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
                </Grid>
            </Grid>
        </ThemeProvider>
    );
}

export default PersonalData;
