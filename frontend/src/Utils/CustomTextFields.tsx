import {styled, TextField} from "@mui/material";

export const ReadOnlyTextField= styled(TextField)({
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
    }
);

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
