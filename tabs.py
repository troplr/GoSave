# -*- coding: utf-8 -*-
import dash
import dash_html_components as html
import dash_core_components as dcc
from dash.dependencies import Input, Output, State
import base64

external_stylesheets = ['https://codepen.io/chriddyp/pen/bWLwgP.css']

app = dash.Dash(__name__, external_stylesheets=external_stylesheets)
app.css.append_css({'external_url': 'https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css'})

image_filename = 'temp_google_map.png'
encoded_image = base64.b64encode(open(image_filename, 'rb').read())

propic_1 = 'propic_1.jpg'
propic_2 = 'propic_2.jpg'
propic_3 = 'propic_3.jpg'
propic_4 = 'propic_4.jpg'
propic_5 = 'propic_5.jpg'
logo = 'logo.png'

encoded_propic_1 = base64.b64encode(open(propic_1, 'rb').read())
encoded_propic_2 = base64.b64encode(open(propic_2, 'rb').read())
encoded_propic_3 = base64.b64encode(open(propic_3, 'rb').read())
encoded_propic_4 = base64.b64encode(open(propic_4, 'rb').read())
encoded_propic_5 = base64.b64encode(open(propic_5, 'rb').read())
encoded_logo = base64.b64encode(open(logo, 'rb').read())

app.layout = html.Div(children=[
    html.Div(className='container', children=[
        html.H1(children='GoSave!', style={'fontSize': 80, 'display': 'inline-block', 'padding-left': 300}),
        html.Img(src='data:image/png;base64,{}'.format(encoded_logo.decode()), style={'width': '12%', 'display': 'inline-block'})
    ]),

    html.Div(children='''
        A new way to save money, and to see how you stack up against your friends.
    ''', style={'fontSize': 20, 'padding-left': 420}),

    dcc.Tabs(id="tabs", children=[
        dcc.Tab(label='Nearby restaurants', children=[
            html.Div(children=[

                dcc.Input(id='user-dish', placeholder='Enter a dish...', type='text', value=''),
                html.Div(children=[
                    dcc.Dropdown(
                        id="drop-down",
                        placeholder='Select a price...',
                        options=[
                                    {'label': '$', 'value': '$'}, {'label': '$$', 'value': '$$'}, {'label': '$$$', 'value': '$$$'}
                                ],
                        value='' 
                )], style={'width': '13.3%'}
                ),
                dcc.Input(id='user-address', placeholder='Enter an address...', type='text', value=''),
                html.Div(id='output-submit'),
                html.Div(id='output-address'),
                html.Div(id='output-price'),
                html.Div(
                    html.Img(src='data:image/png;base64,{}'.format(encoded_image.decode()))
                )
            ], style={'backgroundColor': '#D9FFF8'})
        ]),
        dcc.Tab(label='Your Savings', children=[
            html.Ul([
                html.Div('Amount Saved', style={'font-weight': 'bold', 'fontSize': 40}),
 

                html.Div('  Food', className='icon fa fa-shopping-basket', style={'fontSize': 30, 'padding-top': 30, 'padding-bottom': 30, 'display': 'block'}),
                html.Div('$30.23', style={'fontSize': 25, 'font-weight': 'bold', 'color': '#1B6C08', 'padding-left': 40}),

                html.Div('  Retails', className='icon fa fa-shopping-cart', style={'fontSize': 30, 'padding-top': 30, 'padding-bottom': 30, 'display': 'block'}),
                html.Div('$18.09', style={'fontSize': 25, 'font-weight': 'bold', 'color': '#1B6C08', 'padding-left': 40}),

                html.Div('  Entertainment', className='icon fa fa-film', style={'fontSize': 30, 'padding-top': 30, 'padding-bottom': 30, 'display': 'block'}),
                html.Div('$8.46', style={'fontSize': 25, 'font-weight': 'bold', 'color': '#B91B11', 'padding-left': 40}),

                html.Div('  Transportation', className='icon fa fa-subway',style={'fontSize': 30, 'padding-top': 30, 'padding-bottom': 30, 'display': 'block'}),
                html.Div('$6.81', style={'fontSize': 25, 'font-weight': 'bold', 'color': '#1B6C08', 'padding-left': 40})
            ], style={'display': 'block', 'backgroundColor': '#E0F0DC'})
        ]),
        dcc.Tab(label='Leaderboard', children=[
            html.Div(children=[
                # 
            
                html.Div(className='container', children=[
                    html.Div('1', style={'fontSize': 20, 'font-weight': 'bold', 'color': '#3F69C6', 'display': 'inline-block'}),
                    html.Div(
                        html.Img(src='data:image/jpg;base64,{}'.format(encoded_propic_1.decode()), style={'width': '20%', 'display': 'inline-block'})),
                    html.Div('312 points', style={'fontSize': 30, 'font-weight': 'bold', 'color': '#AD901B', 'float': 'left'})
                ], style={'display': 'flex'}),

                html.Div(className='container', children=[
                    html.Div('2', style={'fontSize': 20, 'font-weight': 'bold', 'color': '#3F69C6', 'display': 'inline-block'}),
                    html.Div(
                        html.Img(src='data:image/jpg;base64,{}'.format(encoded_propic_2.decode()), style={'width': '25%', 'display': 'inline-block'})),
                    html.Div('281 points', style={'fontSize': 30, 'font-weight': 'bold', 'color': '#AD901B', 'float': 'left', 'padding-left': 90})
                ], style={'display': 'flex'}),

                html.Div(className='container', children=[
                    html.Div('3', style={'fontSize': 20, 'font-weight': 'bold', 'color': '#3F69C6', 'display': 'inline-block'}),
                    html.Div(
                        html.Img(src='data:image/jpg;base64,{}'.format(encoded_propic_3.decode()), style={'width': '25%', 'display': 'inline-block'})),
                    html.Div('113 points', style={'fontSize': 30, 'font-weight': 'bold', 'color': '#AD901B', 'float': 'left', 'padding-left': 60})
                ], style={'display': 'flex'}),

                html.Div(className='container', children=[
                    html.Div('4', style={'fontSize': 20, 'font-weight': 'bold', 'color': '#3F69C6', 'display': 'inline-block'}),
                    html.Div(
                        html.Img(src='data:image/jpg;base64,{}'.format(encoded_propic_4.decode()), style={'width': '25%', 'display': 'inline-block'})),
                    html.Div('85 points', style={'fontSize': 30, 'font-weight': 'bold', 'color': '#AD901B', 'float': 'left', 'padding-left': 80})
                ], style={'display': 'flex'}),

                html.Div(className='container', children=[
                    html.Div('5', style={'fontSize': 20, 'font-weight': 'bold', 'color': '#3F69C6', 'display': 'inline-block'}),
                    html.Div(
                        html.Img(src='data:image/jpg;base64,{}'.format(encoded_propic_5.decode()), style={'width': '25%', 'display': 'inline-block'})),
                    html.Div('34 points', style={'fontSize': 30, 'font-weight': 'bold', 'color': '#AD901B', 'float': 'left', 'padding-left': 70})
                ], style={'display': 'flex'})
            ], style={'backgroundColor': '#F5F0DB', 'display': 'block'})
        ])
    ])
])


@app.callback(Output('output-submit', 'children'),
            [Input('user-dish', 'n_submit')],
            [State('user-dish', 'value')])
def update_output(ns1, dish):
    return u'''
        Dish is "{}".
    '''.format(dish)

@app.callback(Output('output-price', 'children'),
            [Input('drop-down', 'value')])
def dropDown_output(value):
    return 'Price is {}'.format(value)

@app.callback(Output('output-address','children'),
            [Input('user-address', 'n_submit')],
            [State('user-address', 'value')])
def address_output(ns1, address):
    return u'''
        Address is "{}"
    '''.format(address)

if __name__ == '__main__':
   app.run_server(debug=True)