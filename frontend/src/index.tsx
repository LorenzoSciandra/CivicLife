import React from 'react';
import ReactDOM from 'react-dom/client';
import './App.css';
import {createTheme, Grid, ThemeProvider,} from "@mui/material";

import {BrowserRouter} from "react-router-dom";
import App from "./App";

const root = ReactDOM.createRoot(
    document.getElementById('root') as HTMLElement
);

const theme = createTheme({
        typography: {
            fontFamily: [
                'ubuntu'
            ].join(','),
        },
    }
);

root.render(
    <React.StrictMode>
        <ThemeProvider theme={theme}>
            <Grid container className="App-header">
               <BrowserRouter>
                   <App />
               </BrowserRouter>
            </Grid>
        </ThemeProvider>
    </React.StrictMode>
);

