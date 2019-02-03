# -*- coding: utf-8 -*-
import dash
import dash_html_components as html
import dash_core_components as dcc
from dash.dependencies import Input, Output, State
import base64

external_stylesheets = ['https://codepen.io/chriddyp/pen/bWLwgP.css']

app = dash.Dash(__name__, external_stylesheets=external_stylesheets)
app.css.append_css({'external_url': 'https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css'})

image_filename = 'temp_google_map.png' # replace with your own image
encoded_image = base64.b64encode(open(image_filename, 'rb').read())

app.layout = html.Div(children=[
    html.H1(children='SavingPro'),

    html.Div(children='''
        A new way to save money, and to see how you stack up against your friends.
    '''),

    dcc.Tabs(id="tabs", children=[
        dcc.Tab(label='Nearby restaurants', children=[
            dcc.Input(id='user-dish', placeholder='Enter a dish...', type='text', value=''),
            html.Div(id='output-submit'),
            html.Div(
                html.Img(src='data:image/png;base64,{}'.format(encoded_image.decode()))
            )
        ]),
        dcc.Tab(label='Your Savings', children=[
            html.Div('Amount Saved', style={'font-weight': 'bold', 'fontSize': 40}),

            html.Div('  Food', className='icon fa fa-utensils', style={'fontSize': 20, 'padding-bottom': 40}),

            html.Div('  Retails', className='icon fa fa-shopping-cart', style={'italic', 'fontSize': 20}),

            html.Div('  Entertainment', className='icon fa fa-film', style={'italic', 'fontSize': 20}),

            html.Div('  Transportation', className='icon fa fa-subway',style={'italic', 'fontSize': 20})
        ]),
        dcc.Tab(label='Leaderboard', children=[
                dcc.Graph(
                    id='example-graph-2',
                    figure={
                        'data': [
                            {'x': [1, 2, 3], 'y': [2, 4, 3],
                                'type': 'bar', 'name': 'SF'},
                            {'x': [1, 2, 3], 'y': [5, 4, 3],
                             'type': 'bar', 'name': u'Montréal'},
                        ]
                    }
                )
        ]),
    ])
])


@app.callback(Output('output-submit', 'children'),
              [Input('user-dish', 'n_submit')],
              [State('user-dish', 'value')])
def update_output(ns1, dish):
    return u'''
        Dish is "{}".
    '''.format(dish)


if __name__ == '__main__':
    app.run_server(debug=True)