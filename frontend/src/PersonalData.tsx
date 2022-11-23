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
import basquiaPulito from "./imgs/basquiaPulito.png"

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
    const [dataList, setDataList] = useState(['ciao', 'ciao2', 'ciao3', 'ciao4', 'ciao5', 'ciao', 'ciao2'])
    const [activeButton, setActiveButton] = useState('data');
    const [dataButton, setDataButton] = useState<ButtonStyleType>(activeButtonColor)
    const [vaxButton, setVaxButton] = useState<ButtonStyleType>(inactiveButtonColor)
    const [voteButton, setVoteButton] = useState<ButtonStyleType>(inactiveButtonColor)


    const handleChange = (event: React.MouseEvent<HTMLElement>, newActiveButton: string,) => {
        if(newActiveButton===buttons[0]){
            setDataButton(activeButtonColor)
            setVaxButton(inactiveButtonColor)
            setVoteButton(inactiveButtonColor)
        }
        else if(newActiveButton===buttons[1]){
            setDataButton(inactiveButtonColor)
            setVaxButton(activeButtonColor)
            setVoteButton(inactiveButtonColor)
        }else if(newActiveButton===buttons[2]){
            setDataButton(inactiveButtonColor)
            setVaxButton(inactiveButtonColor)
            setVoteButton(activeButtonColor)
        }
        setActiveButton(newActiveButton)
    };

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
                    <Grid item xs={12} display="flex">
                        <IconButton><KeyboardBackspaceIcon sx={{fontSize: 60, color: '#ffffff'}}/></IconButton>
                    </Grid>
                    <Grid item xs={12} display="flex" justifyContent="center" alignItems="center">
                        <ToggleButtonGroup
                            value={activeButton}
                            exclusive
                            onChange={handleChange}
                            aria-label="Platform"
                        >
                            <ToggleButton style={{
                                backgroundColor: dataButton.background,
                                color: dataButton.color,
                                borderColor: '#000000',
                                fontSize: '25px'
                            }} value={buttons[0]}>{buttons[0]}</ToggleButton>
                            <ToggleButton style={{
                                backgroundColor: vaxButton.background,
                                color: vaxButton.color,
                                borderColor: '#000000',
                                fontSize: '25px'
                            }} value={buttons[1]}>{buttons[1]}</ToggleButton>
                            <ToggleButton style={{
                                backgroundColor: voteButton.background,
                                color: voteButton.color,
                                borderColor: '#000000',
                                fontSize: '25px'
                            }} value={buttons[2]}>{buttons[2]}</ToggleButton>
                        </ToggleButtonGroup>
                    </Grid>
                    <Grid item xs={12} display="flex" justifyContent="center" alignItems="center">
                        <Box sx={{width: '70%', height: '100%'}}>
                            {dataList.map((value, index) => {
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
                    <Grid item display="flex" alignItems={'center'}>
                        <CardMedia
                            component="img"
                            image={basquiaPulito}
                            sx={{
                                width: '8%',
                            }}
                            alt="Logo"
                        />
                        <Typography
                            style={{color: '#feac0d', textAlign: 'center', fontSize: '1.5rem'}}>CivicLife</Typography>
                    </Grid>
                </Grid>
            </Grid>

        </ThemeProvider>
    );
}

export default PersonalData;
