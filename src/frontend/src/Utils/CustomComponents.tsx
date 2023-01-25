import {Grid, styled, TextField, ToggleButton, ToggleButtonGroup} from "@mui/material";
import Switch from "@mui/material/Switch";
import React, {useState} from "react";

export const CssTextField = styled(TextField)({

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

export const Android12Switch = styled(Switch)(({ theme }) => ({
    padding: 8,
    '& .MuiSwitch-track': {
        borderRadius: 22 / 2,
        '&:before, &:after': {
            content: '""',
            position: 'absolute',
            top: '50%',
            transform: 'translateY(-50%)',
            width: 16,
            height: 16,
        },
        '&:before': {
            backgroundImage: `url('data:image/svg+xml;utf8,<svg xmlns="http://www.w3.org/2000/svg" height="16" width="16" viewBox="0 0 24 24"><path fill="${encodeURIComponent(
                theme.palette.getContrastText(theme.palette.primary.main),
            )}" d="M21,7L9,19L3.5,13.5L4.91,12.09L9,16.17L19.59,5.59L21,7Z"/></svg>')`,
            left: 12,
        },
        '&:after': {
            backgroundImage: `url('data:image/svg+xml;utf8,<svg xmlns="http://www.w3.org/2000/svg" height="16" width="16" viewBox="0 0 24 24"><path fill="${encodeURIComponent(
                theme.palette.getContrastText(theme.palette.primary.main),
            )}" d="M19,13H5V11H19V13Z" /></svg>')`,
            right: 12,
        },
    },
    '& .MuiSwitch-thumb': {
        boxShadow: 'none',
        width: 16,
        height: 16,
        margin: 2,
    },
}));

export type ButtonStyleType = {
    background: string,
    color: string
}
export const activeButtonColor: ButtonStyleType = {
    background: '#feac0d',
    color: '#ffffff'
}
export const inactiveButtonColor: ButtonStyleType = {
    background: '#ffffff',
    color: '#feac0d'
}



type UpperButtonsMenupropsType={
    first_label:string,
    second_label:string,
    third_label:string | null,
    first_list: any[],
    second_list: any[],
    third_list:any[]| null,
    listSetter:any,
    buttonSetter:any|null
}

const UpperButtonMenu = (props: UpperButtonsMenupropsType) => {

    type ButtonStyleType = {
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
    const activeButton = useState(props.first_label);
    const [firstButton, setFirstButton] = useState<ButtonStyleType>(activeButtonColor)
    const [secondButton, setSecondButton] = useState<ButtonStyleType>(inactiveButtonColor)
    const [thirdButton, setThirdButton] = useState<ButtonStyleType>(inactiveButtonColor)

    const handleChange = (event: React.MouseEvent<HTMLElement>, newActiveButton: string,) => {
        if (newActiveButton === props.first_label) {
            setFirstButton(activeButtonColor)
            setSecondButton(inactiveButtonColor)
            setThirdButton(inactiveButtonColor)
            props.listSetter(props.first_list)
        } else if (newActiveButton === props.second_label) {
            setFirstButton(inactiveButtonColor)
            setSecondButton(activeButtonColor)
            setThirdButton(inactiveButtonColor)
            props.listSetter(props.second_list)
        } else if(props.third_list!==null) {
            if (newActiveButton === props.third_label) {
                setFirstButton(inactiveButtonColor)
                setSecondButton(inactiveButtonColor)
                setThirdButton(activeButtonColor)
                props.listSetter(props.third_list)
            }
        }
        if(props.buttonSetter) props.buttonSetter(newActiveButton)
    };

    return (
        <>
            <Grid item xs={12} display="flex" justifyContent="center" alignItems="center" sx={{position:'fixed', top:'8%',margin:'auto', width:'100%'}}>
                <ToggleButtonGroup value={activeButton} exclusive onChange={handleChange} aria-label="Platform">
                    <ToggleButton style={{
                        backgroundColor: firstButton.background,
                        color: firstButton.color,
                        borderColor: '#000000',
                        fontSize: '25px'
                    }} value={props.first_label}>{props.first_label}</ToggleButton>
                    <ToggleButton style={{
                        backgroundColor: secondButton.background,
                        color: secondButton.color,
                        borderColor: '#000000',
                        fontSize: '25px'
                    }} value={props.second_label}>{props.second_label}</ToggleButton>
                    {
                        props.third_list && props.third_label?<ToggleButton style={{
                                backgroundColor: thirdButton.background,
                                color: thirdButton.color,
                                borderColor: '#000000',
                                fontSize: '25px'
                            }} value={props.third_label}>{props.third_label}</ToggleButton>
                            :
                            null
                    }
                </ToggleButtonGroup>
            </Grid>
        </>
    );
}

export default UpperButtonMenu;



